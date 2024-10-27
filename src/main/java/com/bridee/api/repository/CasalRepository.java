package com.bridee.api.repository;

import com.bridee.api.entity.Casal;
import com.bridee.api.projection.orcamento.OrcamentoProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CasalRepository extends JpaRepository<Casal, Integer> {

    boolean existsByEmail(String email);

    @Query("""
            SELECT c FROM Casal c WHERE c.id = :casalId
            """)
    OrcamentoProjection findOrcamentoByCasalId(Integer casalId);

}
