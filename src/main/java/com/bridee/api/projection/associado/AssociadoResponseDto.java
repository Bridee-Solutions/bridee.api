package com.bridee.api.projection.associado;

import lombok.Data;

@Data
public class AssociadoResponseDto {

    private Integer id;
    private String nome;
    private String visaoGeral;
    private String cidade;
    private String bairro;
    private Double notaMedia;
    private Double totalAvaliacoes;
    private String imagemPrincipal;
}
