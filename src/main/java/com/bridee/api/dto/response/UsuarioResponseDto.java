package com.bridee.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "DTO de usuário",
        description = "DTO para devolver as informações de usuário")
public class UsuarioResponseDto {

    private int id;
    @Schema(description = "Nome do usuário", example = "XPTO")
    private String nome;
    @Schema(description = "Email do usuário", example = "xpto@example.com")
    private String email;
    @Schema(description = "Telefone do usuário", example = "5599889889743")
    private String telefone;
    private String estadoCivil;
    private Boolean enabled;
}
