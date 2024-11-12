package com.bridee.api.repository;

import com.bridee.api.entity.Atividade;
import com.bridee.api.entity.Cronograma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CronogramaRepository extends JpaRepository<Cronograma, Integer> {

    @Query("""
            SELECT c FROM Cronograma c JOIN FETCH c.atividades WHERE c.casamento.id = :casamentoId
            """)
    Cronograma findAtividadesCronogramaByCasamentoId(Integer casamentoId);

}
