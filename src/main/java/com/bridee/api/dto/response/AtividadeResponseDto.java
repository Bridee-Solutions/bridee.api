package com.bridee.api.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AtividadeResponseDto {

    private Integer id;
    private String nome;
    private LocalDateTime inicio;
    private LocalDateTime fim;

}
