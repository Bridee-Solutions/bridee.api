package com.bridee.api.repository;

import com.bridee.api.entity.Casal;
import com.bridee.api.repository.projection.orcamento.OrcamentoProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CasalRepository extends JpaRepository<Casal, Integer> {

    boolean existsByEmail(String email);

    @Query("""
            SELECT c FROM Casal c WHERE c.id = :casalId
            """)
    OrcamentoProjection findOrcamentoByCasalId(Integer casalId);

    @Query("""
            SELECT c.casal.id FROM Casamento c WHERE c.id = :casamentoId
            """)
    Optional<Integer> findCasalIdByCasamentoId(Integer casamentoId);

    @Query("""
            SELECT c.id FROM Casal c WHERE c.email = :email
            """)
    Integer findIdByEmail(String email);

    Optional<Casal> findByEmail(String email);
}
