package com.bridee.api.repository;

import com.bridee.api.entity.ItemOrcamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemOrcamentoRepository extends JpaRepository<ItemOrcamento, Integer> {
    boolean existsByTipoAndCasalId(String tipo, Integer casalId);
}
