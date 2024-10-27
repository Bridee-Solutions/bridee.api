package com.bridee.api.dto.response.externo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "DTO de assessor externo")
public class AssessorExternoResponseDto extends UsuarioExternoResponseDto{

    @Schema(description = "Cnpj do assessor", example = "26342970000169")
    private String cnpj;
    @Schema(description = "Valor", example = "3000.0")
    private BigDecimal preco;
    @Schema(description = "premium", example = "true")
    private Boolean premium;

}
