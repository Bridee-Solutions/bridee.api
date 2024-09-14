package com.bridee.api.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class AssessorRequestDto extends UsuarioRequestDto{

    private String cnpj;
    private BigDecimal preco;
    private Boolean premium;

}
