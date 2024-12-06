package com.bridee.api.dto.response;

import lombok.Data;

import java.util.List;

import com.bridee.api.entity.FormaPagamento;
import com.bridee.api.entity.TipoCasamento;
import com.bridee.api.entity.TipoCerimonia;

@Data
public class InformacaoAssociadoResponseDto {

    private Integer id;
    private String visaoGeral;
    private String servicosOferecidos;
    private String formaDeTrabalho;
    private String tamanhoCasamento;
    private String email;
    private String urlSite;
    private Integer assessorId;
    private String cidade;
    private String bairro;
    private List<FormaPagamento> formasPagamento;
    private List<TipoCasamento> tiposCasamento;
    private List<TipoCerimonia> tiposCerimonia;
    private ImagemResponseDto imagemPrimaria;
    private ImagemResponseDto imagensSecundarias;;
}
