package com.bridee.api.service;

import com.bridee.api.dto.request.EmailDto;
import com.bridee.api.dto.security.SecurityUser;
import com.bridee.api.entity.Assessor;
import com.bridee.api.entity.Casal;
import com.bridee.api.entity.Usuario;
import com.bridee.api.entity.enums.email.CadastroEmailFields;
import com.bridee.api.entity.enums.email.EmailFields;
import com.bridee.api.entity.enums.email.EmailTemplate;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.repository.UsuarioRoleRepository;
import com.bridee.api.utils.EmailTemplateBuilder;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bridee.api.repository.UsuarioRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UsuarioService implements UserDetailsService {

    @Value("${email.register.url}")
    private String registerUrl;
    private final UsuarioRepository repository;
    private final UsuarioRoleRepository usuarioRoleRepository;
    private final EmailService emailService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new SecurityUser(repository.findByEmail(username).orElseThrow(ResourceNotFoundException::new), usuarioRoleRepository);
    }

    public Usuario findByEmail(String email){
        return repository.findByEmail(email).orElseThrow(ResourceNotFoundException::new);
    }

    public void sendRegistrationEmail(Usuario usuario){

        if (Objects.isNull(usuario)){
            throw new IllegalArgumentException("Usuario não encontrado!");
        }

        Map<EmailFields, Object> emailFields = new HashMap<>();
        emailFields.put(CadastroEmailFields.REGISTER_URL, registerUrl);

        if(usuario instanceof Casal){
            emailFields.put(CadastroEmailFields.COUPLE_NAME, "%s & %s".formatted(usuario.getNome(), ((Casal) usuario).getNomeParceiro()));
            emailFields.put(CadastroEmailFields.IS_ASSESSOR, false);
        }else if(usuario instanceof Assessor){
            emailFields.put(CadastroEmailFields.ASSESSOR_NAME, "%s".formatted(usuario.getNome()));
            emailFields.put(CadastroEmailFields.IS_ASSESSOR, true);
        }

        String emailHtml = EmailTemplateBuilder.generateHtmlEmailTemplate(EmailTemplate.CADASTRO, emailFields);
        EmailDto emailDto = EmailDto.builder()
                .to(usuario.getEmail())
                .subject("Confirmação de cadastro.")
                .message(emailHtml)
                .isHTML(true)
                .build();
        emailService.sendEmail(emailDto);
    }
}
