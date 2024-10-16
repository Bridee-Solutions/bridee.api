package com.bridee.api.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class MesaResponseDto {

    private Integer id;
    private String nome;
    private Integer numeroAssentos;
    private List<ConvidadoResponseDto> convidados;

}
