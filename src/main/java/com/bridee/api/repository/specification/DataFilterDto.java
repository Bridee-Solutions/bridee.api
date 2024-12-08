package com.bridee.api.repository.specification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataFilterDto {

    private Integer ano;
    private LocalDate dataInicio;
    private LocalDate dataFim;

    public DataFilterDto(LocalDate dataInicio, LocalDate dataFim) {
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }
}
