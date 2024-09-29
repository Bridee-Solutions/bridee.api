package com.bridee.api.dto.request.externo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class AssessorExternoRequestDto extends UsuarioExternoRequestDto {

    private String cnpj;
    private BigDecimal preco;
    private Boolean premium;

}
