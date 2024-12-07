package com.bridee.api.projection.orcamento.fornecedor;

import com.bridee.api.projection.orcamento.SubcategoriaProjection;

public interface FornecedorProjection extends FornecedorBaseProjection{

    SubcategoriaProjection getSubcategoriaServico();

}
