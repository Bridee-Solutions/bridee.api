package com.bridee.api.repository.projection.orcamento.fornecedor;

import com.bridee.api.repository.projection.orcamento.SubcategoriaProjection;

public interface FornecedorProjection extends FornecedorBaseProjection{

    SubcategoriaProjection getSubcategoriaServico();

}
