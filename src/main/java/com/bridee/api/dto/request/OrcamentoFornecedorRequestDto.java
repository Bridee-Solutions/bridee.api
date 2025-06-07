package com.bridee.api.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrcamentoFornecedorRequestDto {

    private Integer id;

    @NotNull
    @PositiveOrZero
    private BigDecimal preco;

    @NotNull
    @Positive
    private Integer fornecedorId;
}
