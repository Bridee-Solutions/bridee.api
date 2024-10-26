package com.bridee.api.dto.request.externo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuarioExternoRequestDto {

    private int id;
    @Size(min = 3)
    @NotBlank
    @Schema(description = "Nome do usuário", example = "Clodoalvis")
    private String nome;
    @Email
    @Schema(description = "Email do usuário", example = "casal@example.com")
    private String email;
    @Size(max = 11, min = 11)
    @Schema(description = "Telefone do usuário", example = "usuario@example.com")
    private String telefone;
    @Schema(description = "Estado civíl", example = "SOLTEIRO")
    private String estadoCivil;
    @Schema(description = "Se o usuário é externo", example = "true")
    private Boolean externo = true;
}
