package com.bridee.api.dto.response.externo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "DTO de casal",
        description = "DTO para devolver os dados de casais externos")
public class CasalExternoResponseDto extends UsuarioExternoResponseDto{

    @Schema(description = "Nome do parceiro", example = "Rebeca")
    private String nomeParceiro;
    @Schema(description = "Telefone do parceiro", example = "55998332542")
    private String telefoneParceiro;
    private String endereco;
    private String cep;

}
