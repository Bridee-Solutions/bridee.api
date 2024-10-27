package com.bridee.api.repository;

import com.bridee.api.entity.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MesaRepository extends JpaRepository<Mesa, Integer> {
    List<Mesa> findAllByCasamentoId(Integer casamentoId);

    boolean existsByNomeAndCasamentoId(String nome, Integer casamentoId);
}
