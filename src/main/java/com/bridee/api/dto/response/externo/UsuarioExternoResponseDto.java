package com.bridee.api.dto.response.externo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema(name = "DTO de usuário externo")
public class UsuarioExternoResponseDto {

    private int id;
    @Schema(description = "Nome do usuário", example = "XPTO")
    private String nome;
    @Schema(description = "Email do usuário", example = "xpto@example.com")
    private String email;
    @Schema(description = "Telefone do usuário", example = "5599889889743")
    private String telefone;
    private String estadoCivil;

}
