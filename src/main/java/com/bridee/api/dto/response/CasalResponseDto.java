package com.bridee.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "Response dto de casal",
        description = "DTO para devolver as informações do casal")
public class CasalResponseDto extends UsuarioResponseDto{

    @Schema(description = "Nome do parceiro", example = "Rebeca")
    private String nomeParceiro;
    @Schema(description = "Telefone do parceiro", example = "55998332542")
    private String telefoneParceiro;
    private String endereco;
    private String cep;
}
