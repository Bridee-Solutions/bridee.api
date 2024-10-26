package com.bridee.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Schema(name = "Casal Orcamento response DTO",
        description = "Retorna o orçamento de um casal")
public class CasalOrcamentoResponseDto {

    @Schema(description = "Orcamento total do casal", example = "60.000")
    private BigDecimal orcamentoTotal;
    @Schema(description = "Orcamento gasto até o momento", example = "40.000")
    private BigDecimal orcamentoGasto;
    @Schema(description = "Nome do casal", example = "Clodoaldo & Rebeca")
    private String nomeCasal;
    @Schema(description = "Itens que compõem o orçamento")
    private List<ItemOrcamentoResponseDto> itemOrcamentos = new ArrayList<>();
    @Schema(description = "Fornecedores que compõem o orçamento")
    private List<OrcamentoFornecedorResponseDto> orcamentoFornecedores = new ArrayList<>();

}
