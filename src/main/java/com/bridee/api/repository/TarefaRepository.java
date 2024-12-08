package com.bridee.api.repository;

import com.bridee.api.entity.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TarefaRepository extends JpaRepository<Tarefa, Integer>, JpaSpecificationExecutor<Tarefa> {

    @Query("""
            SELECT YEAR(t.tarefa.dataLimite) FROM TarefaCasal t WHERE t.casal.id = :casalId GROUP BY YEAR(t.tarefa.dataLimite)
            """)
    List<Integer> findAllYearsFromTasks(Integer casalId);

}
