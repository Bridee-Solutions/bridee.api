package com.bridee.api.projection.orcamento;

import java.math.BigDecimal;
import java.util.List;

public interface OrcamentoFornecedorProjection {

    Integer getId();
    BigDecimal getPreco();
    FornecedorProjection getFornecedor();
}
