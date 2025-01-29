package com.bridee.api.repository;

import com.bridee.api.entity.Casamento;
import com.bridee.api.repository.projection.casamento.CasamentoDateProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CasamentoRepository extends JpaRepository<Casamento, Integer> {

    @Modifying
    @Query("""
            UPDATE Casamento c SET c.mensagemConvite = :message WHERE c.id = :casamentoId
            """)
    int updateCasamentoMessage(Integer casamentoId, String message);

    @Query("""
            SELECT ca.id FROM Casamento ca WHERE ca.casal.id = :casalId
            """)
    int findCasamentoIdByCasalId(Integer casalId);

    @Query("""
            SELECT c.dataFim FROM Casamento c WHERE c.id = :id
            """)
    CasamentoDateProjection findDataFimByCasamentoId(Integer id);
}
