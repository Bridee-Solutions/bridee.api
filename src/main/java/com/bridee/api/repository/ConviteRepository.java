package com.bridee.api.repository;

import com.bridee.api.entity.Convite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConviteRepository extends JpaRepository<Convite, Integer> {

    List<Convite> findAllByCasamentoId(Integer casamentoId);
}
