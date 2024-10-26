package com.bridee.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "DTO para envio de e-mail",
        description = "DTO para enviar dados por e-mail")
public class EmailDto {

    @NotBlank
    @Email
    @Schema(description = "Email do destinatário", example = "destinatario@example.com")
    private String to;
    @NotBlank
    @Email
    @Schema(description = "Assunto do e-mail", example = "Criação de conta")
    private String subject;
    @NotBlank
    @Schema(description = "Mensagem do e-mail", example = "Lorem ipsum")
    private String message;
    @Schema(description = "Se a mensagem é HTML", example = "true")
    private boolean isHTML;

}
