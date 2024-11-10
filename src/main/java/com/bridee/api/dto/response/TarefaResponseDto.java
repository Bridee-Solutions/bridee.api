package com.bridee.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TarefaResponseDto {

    private Integer id;
    private String nome;
    private String descricao;
    private String status;
    private String categoria;
    private LocalDate dataLimite;
    private Integer mesesAnteriores;

}
