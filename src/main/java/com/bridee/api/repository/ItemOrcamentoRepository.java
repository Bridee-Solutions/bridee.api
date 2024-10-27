package com.bridee.api.repository;

import com.bridee.api.entity.ItemOrcamento;
import com.bridee.api.projection.orcamento.ItemOrcamentoProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemOrcamentoRepository extends JpaRepository<ItemOrcamento, Integer> {
    boolean existsByTipoAndCasalId(String tipo, Integer casalId);

    @Query("""
            SELECT item FROM ItemOrcamento item JOIN FETCH item.custos WHERE item.casal.id = :casalId
            """)
    List<ItemOrcamentoProjection> findAllByCasalId(Integer casalId);
}
