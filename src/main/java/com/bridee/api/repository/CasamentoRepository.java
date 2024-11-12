package com.bridee.api.repository;

import com.bridee.api.entity.Casamento;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CasamentoRepository extends JpaRepository<Casamento, Integer> {

    @Modifying
    @Query("""
            UPDATE Casamento c SET c.mensagemConvite = :message WHERE c.id = :casamentoId
            """)
    int updateCasamentoMessage(Integer casamentoId, String message);

}
