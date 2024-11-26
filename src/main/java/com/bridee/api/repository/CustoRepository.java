package com.bridee.api.repository;

import com.bridee.api.entity.Custo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustoRepository extends JpaRepository<Custo, Integer> {
    boolean existsByNomeAndItemOrcamentoId(String nome, Integer itemOrcamentoId);

    List<Custo> findAllByItemOrcamentoId(Integer orcamentoId);

    @Query("""
            SELECT SUM(c.precoAtual) FROM Custo c WHERE c.itemOrcamento.casal.id = :casalId
            """)
    long totalPriceItens(Integer casalId);
}
