package com.bridee.api.dto.request;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(name = "Usuario DTO",
        description = "DTO para enviar os dados de usuário")
public class UsuarioRequestDto {

    private int id;
    @Size(min = 3)
    @NotBlank
    @Schema(description = "Nome do usuário", example = "User")
    private String nome;
    @Email
    @Schema(description = "Email do usuário", example = "userxpto@example.com")
    private String email;
    @Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")
    @NotBlank
    @Schema(description = "Senha do usuário", example = "******")
    private String senha;
    @Size(max = 11, min = 11)
    @Schema(description = "Telefone do usuário", example = "5511322445698")
    private String telefone;
    @Schema(description = "Estado civil do usuário", example = "SOLTEIRO")
    private String estadoCivil;
}
