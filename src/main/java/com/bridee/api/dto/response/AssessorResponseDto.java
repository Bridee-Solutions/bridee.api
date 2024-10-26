package com.bridee.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "DTO de assessor",
        description = "DTO para devolver informações dos assessores")
public class AssessorResponseDto extends UsuarioResponseDto{

    @Schema(description = "Cnpj do assessor", example = "17215584000115")
    private String cnpj;
    @Schema(description = "Valor do serviço", example = "3000.0")
    private BigDecimal preco;
    @Schema(example = "true")
    private Boolean premium;

}
