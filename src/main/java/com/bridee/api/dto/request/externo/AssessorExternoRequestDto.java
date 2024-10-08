package com.bridee.api.dto.request.externo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.br.CNPJ;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class AssessorExternoRequestDto extends UsuarioExternoRequestDto {

    @CNPJ
    @NotBlank
    private String cnpj;
    @Positive
    @NotNull
    private BigDecimal preco;
    private Boolean premium;

}
