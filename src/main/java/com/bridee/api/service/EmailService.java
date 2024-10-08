package com.bridee.api.service;

import com.bridee.api.dto.request.EmailDto;
import com.bridee.api.entity.Assessor;
import com.bridee.api.entity.Casal;
import com.bridee.api.entity.Usuario;
import com.bridee.api.entity.VerificationToken;
import com.bridee.api.entity.enums.blob.EmailImageEnum;
import com.bridee.api.entity.enums.email.fields.CadastroEmailFields;
import com.bridee.api.entity.enums.email.fields.EmailFields;
import com.bridee.api.entity.enums.email.template.EmailTemplate;
import com.bridee.api.pattern.strategy.impl.EmailSender;
import com.bridee.api.utils.AzureBlobStorageProperties;
import com.bridee.api.utils.EmailTemplateBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${email.register.url}")
    private String registerUrl;
    private final AzureBlobStorageProperties azureProperties;
    private final EmailSender emailSender;
    private final VerificationTokenService verificationTokenService;

    public String sendEmail(EmailDto emailDto){
            return emailSender.sendMessage(emailDto);
    }

    public void sendRegistrationEmail(Usuario usuario){

        if (Objects.isNull(usuario)){
            throw new IllegalArgumentException("Usuario não encontrado!");
        }

        var verificationToken = verificationTokenService.generateVerificationToken(usuario);

        CompletableFuture.runAsync(() -> {
            var emailFields = buildEmailRegistrationFields(usuario, verificationToken);

            String emailHtml = EmailTemplateBuilder.generateHtmlEmailTemplate(EmailTemplate.CADASTRO, emailFields);
            EmailDto emailDto = EmailDto.builder()
                    .to(usuario.getEmail())
                    .subject("Confirmação de cadastro.")
                    .message(emailHtml)
                    .isHTML(true)
                    .build();
            sendEmail(emailDto);
        });
    }

    public Map<EmailFields, Object> buildEmailRegistrationFields(Usuario usuario, VerificationToken verificationToken){

        String bodyImageName = EmailImageEnum.CASAL_IMAGE.getValue();
        String bodyImageUrl = azureProperties.generateImageUrlByImageName(bodyImageName);

        String logoImageName = EmailImageEnum.LOGO.getValue();
        String logoImageUrl = azureProperties.generateImageUrlByImageName(logoImageName);

        Map<EmailFields, Object> emailFields = new HashMap<>();
        emailFields.put(CadastroEmailFields.REGISTER_URL, registerUrl);
        emailFields.put(CadastroEmailFields.VERIFICATION_TOKEN, verificationToken.getValor());
        emailFields.put(CadastroEmailFields.BODY_IMAGE, bodyImageUrl);
        emailFields.put(CadastroEmailFields.LOGO, logoImageUrl);

        if(usuario instanceof Casal){
            emailFields.put(CadastroEmailFields.COUPLE_NAME, "%s & %s".formatted(usuario.getNome(), ((Casal) usuario).getNomeParceiro()));
            emailFields.put(CadastroEmailFields.IS_ASSESSOR, false);
        }else if(usuario instanceof Assessor){
            emailFields.put(CadastroEmailFields.ASSESSOR_NAME, "%s".formatted(usuario.getNome()));
            emailFields.put(CadastroEmailFields.IS_ASSESSOR, true);
        }

        return emailFields;
    }
}
