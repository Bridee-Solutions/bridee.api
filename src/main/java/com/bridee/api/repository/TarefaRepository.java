package com.bridee.api.repository;

import com.bridee.api.entity.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarefaRepository extends JpaRepository<Tarefa, Integer> {
}
