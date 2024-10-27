package com.bridee.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(name = "Authentication Request DTO",
        description = "DTO para autenticar o usuário na aplicação")
public class AuthenticationRequestDto {

    @Email
    @Schema(description = "E-mail do usuário", example = "email@example.com")
    private String email;
    @NotBlank
    @Schema(description = "Senha do usuário", example = "******")
    private String senha;
}
