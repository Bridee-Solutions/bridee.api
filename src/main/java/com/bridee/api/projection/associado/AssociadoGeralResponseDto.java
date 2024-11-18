package com.bridee.api.projection.associado;

import lombok.Data;

import java.util.List;

@Data
public class AssociadoGeralResponseDto {

    private Integer id;
    private String nome;
    private String visaoGeral;
    private String cidade;
    private String bairro;
    private Boolean isCasamentoCatolico;
    private Double notaMedia;
    private Double totalAvaliacoes;
    private String siteUrl;
    private String servicosFornecidos;
    private String formaDeTrabalho;
    private String qtdConvidados;
    private List<byte[]> imagens;
    private List<String> formasPagamento;
    private List<String> tiposCasamento;

}
