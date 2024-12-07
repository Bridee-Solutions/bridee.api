package com.bridee.api.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrcamentoTotalRequestDto {

    @PositiveOrZero
    @NotNull
    private BigDecimal orcamentoTotal;

}
