package com.bridee.api.entity.enums.email.fields;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OrcamentoEmailFields implements EmailFields{

    NOME_CASAL("nomeCasal"), EMAIL_CASAL("emailCasal"), TELEFONE("telefone"),
    DATA_CASAMENTO("dataCasamento"), QUANTIDADE_CONVIDADOS("qtdConvidados"),
    LOGO("logoImage"), CASAL_MESSAGE("casalMessage");

    private final String value;

    @Override
    public String getValue() {
        return value;
    }
}
