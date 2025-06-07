package com.bridee.api.repository.projection.orcamento;

import com.bridee.api.repository.projection.orcamento.orcamentofornecedor.OrcamentoFornecedorProjection;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.util.LinkedList;

public interface OrcamentoProjection {

    BigDecimal getOrcamentoTotal();
    @Value("#{target.nome + \"&\" + target.nomeParceiro }")
    String getNomeCasal();
    @Value("#{@orcamentoService.calculateTotalOrcamento(target.id)}")
    BigDecimal getOrcamentoGasto();
    LinkedList<ItemOrcamentoProjection> getItemOrcamentos();
    LinkedList<OrcamentoFornecedorProjection> getOrcamentoFornecedores();

}
