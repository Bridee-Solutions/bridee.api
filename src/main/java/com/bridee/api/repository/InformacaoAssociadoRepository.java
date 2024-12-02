package com.bridee.api.repository;

import com.bridee.api.entity.InformacaoAssociado;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InformacaoAssociadoRepository extends JpaRepository<InformacaoAssociado, Integer> {
    Optional<InformacaoAssociado> findByAssessorId(Integer id);
}
