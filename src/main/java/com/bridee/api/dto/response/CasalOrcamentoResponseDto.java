package com.bridee.api.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CasalOrcamentoResponseDto {

    private BigDecimal orcamentoTotal;
    private BigDecimal orcamentoGasto;
    private List<ItemOrcamentoResponseDto> itemOrcamentos;
    private List<OrcamentoFornecedorResponseDto> orcamentoFornecedores;

}
