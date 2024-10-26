package com.bridee.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "DTO de request do casal",
        description = "DTO para enviar as informações de casal")
public class CasalRequestDto extends UsuarioRequestDto{

    @Size(min = 3)
    @Schema(description = "Nome do parceiro", example = "nome")
    private String nomeParceiro;
    @Size(min = 11, max = 11)
    @Schema(description = "telefone do parceiro", example = "+xx(xx)xxxxxxxxx")
    private String telefoneParceiro;
    private String endereco;
    @Size(min = 8, max = 8)
    @Schema(description = "CEP do endereço", example = "08140300")
    private String cep;

}
