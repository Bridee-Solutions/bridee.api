package com.bridee.api.repository;

import com.bridee.api.entity.Custo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustoRepository extends JpaRepository<Custo, Integer> {
    boolean existsByNomeAndItemOrcamentoId(String nome, Integer itemOrcamentoId);
}
