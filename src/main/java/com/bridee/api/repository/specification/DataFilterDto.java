package com.bridee.api.repository.specification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataFilterDto {

    private LocalDate dataInicio;
    private LocalDate dataFim;

}
