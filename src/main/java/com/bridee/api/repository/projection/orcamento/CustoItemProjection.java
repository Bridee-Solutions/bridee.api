package com.bridee.api.repository.projection.orcamento;

import java.math.BigDecimal;

public interface CustoItemProjection {

    Integer getId();
    String getNome();
    BigDecimal getPrecoAtual();

}
