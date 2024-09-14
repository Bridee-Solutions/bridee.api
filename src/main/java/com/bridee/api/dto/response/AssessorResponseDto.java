package com.bridee.api.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class AssessorResponseDto extends UsuarioResponseDto{

    private String cnpj;
    private BigDecimal preco;
    private Boolean premium;

}
