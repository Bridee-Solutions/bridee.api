package com.bridee.api.projection.orcamento;

import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public interface OrcamentoProjection {

    BigDecimal getOrcamentoTotal();
    @Value("#{target.nome + \" \" + target.nomeParceiro }")
    String getNomeCasal();
    @Value("#{@orcamentoService.calculateTotalOrcamento(target)}")
    BigDecimal getOrcamentoGasto();
    LinkedList<ItemOrcamentoProjection> getItemOrcamentos();
    LinkedList<OrcamentoFornecedorProjection> getOrcamentoFornecedores();

}