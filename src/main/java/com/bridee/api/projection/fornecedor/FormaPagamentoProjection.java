package com.bridee.api.projection.fornecedor;

import java.util.List;

public interface FormaPagamentoProjection {

    List<FormaPagamentoNomeProjection> getNomeForma();

    interface FormaPagamentoNomeProjection {
        String getNome();
    }

}
