package com.bridee.api.repository;

import com.bridee.api.entity.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Integer> {
    
    Optional<Avaliacao> findByFornecedorIdAndCasalId(Integer fornecedorId, Integer casalId);

    Optional<Avaliacao> findByAssessorIdAndCasalId(Integer assessorId, Integer casalId);
}
