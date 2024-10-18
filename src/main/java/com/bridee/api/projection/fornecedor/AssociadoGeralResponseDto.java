package com.bridee.api.projection.fornecedor;

import lombok.Data;

import java.util.List;

@Data
public class AssociadoGeralResponseDto {

    private Integer id;
    private String nome;
    private String visaoGeral;
    private String local;
    private Double notaMedia;
    private Double totalAvaliacoes;
    private String siteUrl;
    private String servicosFornecidos;
    private String formaDeTrabalho;
    private String qtdConvidados;
    private List<String> imagens;
    private List<String> formasPagamento;

}
