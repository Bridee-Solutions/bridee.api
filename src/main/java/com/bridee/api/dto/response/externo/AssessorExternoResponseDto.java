package com.bridee.api.dto.response.externo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class AssessorExternoResponseDto extends UsuarioExternoResponseDto{

    private String cnpj;
    private BigDecimal preco;
    private Boolean premium;

}
