package com.bridee.api.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class ConvitesResponseDto {

    private Integer id;
    private String nome;
    private String pin;
    private List<ConvidadoResponseDto> convidados;

}
