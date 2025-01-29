package com.bridee.api.repository.projection.orcamento.orcamentofornecedor;

import com.bridee.api.repository.projection.orcamento.fornecedor.FornecedorProjection;

public interface OrcamentoFornecedorProjection extends OrcamentoFornecedorBaseProjection {

    FornecedorProjection getFornecedor();

}
