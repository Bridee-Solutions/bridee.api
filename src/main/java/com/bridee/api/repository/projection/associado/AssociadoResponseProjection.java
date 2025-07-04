package com.bridee.api.repository.projection.associado;

public interface AssociadoResponseProjection {

    Integer getId();
    Integer getInformacaoAssociadoId();
    String getNome();
    String getVisaoGeral();
    String getCidade();
    String getBairro();
    Double getNotaMedia();
    Double getTotalAvaliacoes();
}
