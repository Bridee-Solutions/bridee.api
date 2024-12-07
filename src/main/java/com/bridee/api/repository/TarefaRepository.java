package com.bridee.api.repository;

import com.bridee.api.entity.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TarefaRepository extends JpaRepository<Tarefa, Integer>, JpaSpecificationExecutor<Tarefa> {
}
