package com.bridee.api.repository;

import com.bridee.api.entity.Convite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface ConviteRepository extends JpaRepository<Convite, Integer>, JpaSpecificationExecutor<Convite> {

    boolean existsByNomeAndCasamentoId(String nome, Integer casamentoId);
}
