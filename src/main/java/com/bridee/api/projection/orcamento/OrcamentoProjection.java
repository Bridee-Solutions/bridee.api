package com.bridee.api.projection.orcamento;

import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.util.List;

public interface OrcamentoProjection {

    BigDecimal getOrcamentoTotal();
    @Value("#{@orcamentoService.calculateTotalOrcamento(target)}")
    BigDecimal getOrcamentoGasto();
    List<ItemOrcamentoProjection> getItemOrcamentos();
    List<OrcamentoFornecedorProjection> getOrcamentoFornecedores();

}
