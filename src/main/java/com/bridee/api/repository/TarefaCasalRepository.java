package com.bridee.api.repository;

import com.bridee.api.entity.TarefaCasal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarefaCasalRepository extends JpaRepository<TarefaCasal, Integer> {
    boolean existsByTarefaNomeAndCasalId(String nome, Integer casalId);
}
