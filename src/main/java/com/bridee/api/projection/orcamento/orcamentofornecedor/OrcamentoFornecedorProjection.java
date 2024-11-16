package com.bridee.api.projection.orcamento.orcamentofornecedor;

import com.bridee.api.projection.orcamento.fornecedor.FornecedorProjection;

import java.math.BigDecimal;

public interface OrcamentoFornecedorProjection extends OrcamentoFornecedorBaseProjection {

    FornecedorProjection getFornecedor();

}
