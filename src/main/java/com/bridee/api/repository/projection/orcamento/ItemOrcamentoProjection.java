package com.bridee.api.repository.projection.orcamento;

import java.util.List;

public interface ItemOrcamentoProjection {

    Integer getId();
    String getTipo();
    List<CustoItemProjection> getCustos();

}
