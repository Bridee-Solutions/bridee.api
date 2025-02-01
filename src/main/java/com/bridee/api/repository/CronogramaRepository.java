package com.bridee.api.repository;

import com.bridee.api.entity.Cronograma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CronogramaRepository extends JpaRepository<Cronograma, Integer> {

    @Query("""
            SELECT c FROM Cronograma c JOIN FETCH c.atividades WHERE c.casamento.id = :casamentoId
            """)
    Cronograma findCronogramaByCasamentoId(Integer casamentoId);

    @Query("""
            SELECT COUNT(c) > 0 FROM Cronograma c WHERE element(c.atividades).nome = :nomeAtividade AND c.id = :cronogramaId
            """)
    boolean existsAtividadeByNomeInCronograma(String nomeAtividade, Integer cronogramaId);

    boolean existsByCasamentoId(Integer casamentoId);
}
