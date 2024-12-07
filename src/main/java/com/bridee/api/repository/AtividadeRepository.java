package com.bridee.api.repository;

import com.bridee.api.entity.Atividade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtividadeRepository extends JpaRepository<Atividade, Integer> {
    boolean existsByNome(String nome);
}
