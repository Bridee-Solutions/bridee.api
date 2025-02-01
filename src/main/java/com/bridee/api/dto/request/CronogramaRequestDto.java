package com.bridee.api.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CronogramaRequestDto {

    @NotNull
    @Positive
    private Integer casamentoId;
    private List<AtividadeRequestDto> atividades = new ArrayList<>();

}
