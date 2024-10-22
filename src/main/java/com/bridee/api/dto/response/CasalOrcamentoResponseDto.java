package com.bridee.api.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class CasalOrcamentoResponseDto {

    private BigDecimal orcamentoTotal;
    private BigDecimal orcamentoGasto;
    private String nomeCasal;
    private List<ItemOrcamentoResponseDto> itemOrcamentos = new ArrayList<>();
    private List<OrcamentoFornecedorResponseDto> orcamentoFornecedores = new ArrayList<>();

}
