package com.bridee.api.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CasamentoResponseDto {

    private Integer id;
    private String nome;
    private LocalDate dataFim;
    private String tamanhoCasamento;
    private String local;

}
