package com.bridee.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TarefasResponseDto {

    private Integer ano;
    private YearlyTasks tarefas;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class YearlyTasks{
        private List<TarefaResponseDto> janeiro;
        private List<TarefaResponseDto> fevereiro;
        private List<TarefaResponseDto> marco;
        private List<TarefaResponseDto> abril;
        private List<TarefaResponseDto> maio;
        private List<TarefaResponseDto> junho;
        private List<TarefaResponseDto> julho;
        private List<TarefaResponseDto> agosto;
        private List<TarefaResponseDto> setembro;
        private List<TarefaResponseDto> outubro;
        private List<TarefaResponseDto> novembro;
        private List<TarefaResponseDto> dezembro;
        private List<TarefaResponseDto> atrasadas;
    }

}
