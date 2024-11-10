package com.bridee.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResponseDto {

    private List<OrcamentoFornecedorResponseDto> orcamentoFornecedorResponse;
    private AssessorResponseDto assessorResponseDto;
    private DashboardAssentos assentosResumo;
    private DashboardOrcamento orcamento;
    private DashboardTarefa tarefas;
    private DashboardCasamento casamentoInfo;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DashboardAssentos {

        private Integer totalMesas;
        private Integer convidadosSentados;
        private Integer totalConvidados;

    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DashboardOrcamento{
        private BigDecimal orcamentoTotal;
        private BigDecimal orcamentoGasto;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DashboardTarefa{
        private List<TarefaResponseDto> ultimasTarefas;
        private Integer totalItens;
        private Integer totalConcluidos;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DashboardCasamento{
        private Integer quantidadeConvidados;
        private LocalDate dataCasamento;
        private String local;
    }

}
