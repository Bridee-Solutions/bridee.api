package com.bridee.api.service;

import com.bridee.api.entity.Avaliacao;
import com.bridee.api.exception.ResourceAlreadyExists;
import com.bridee.api.repository.AvaliacaoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AvaliacaoService {

    private final AvaliacaoRepository repository;

    @Transactional
    public Avaliacao save(Avaliacao avaliacao){
        Integer nota = avaliacao.getNota();
        avaliacao = avaliacaoToBeInserted(avaliacao);
        avaliacao.setNota(nota);
        return repository.save(avaliacao);
    }

    private Avaliacao avaliacaoToBeInserted(Avaliacao avaliacao){

        Integer casalId = avaliacao.getCasal().getId();
        Integer fornecedorId = Objects.nonNull(avaliacao.getFornecedor()) ? avaliacao.getFornecedor().getId() : null;
        Integer assessorId = Objects.nonNull(avaliacao.getAssessor()) ? avaliacao.getAssessor().getId() : null;

        validateFornecedorAndAssessorIds(fornecedorId, assessorId);

        if (Objects.nonNull(fornecedorId)){
            Optional<Avaliacao> optionalAvaliacaoFornecedor = repository.findByFornecedorIdAndCasalId(fornecedorId, casalId);
            if (optionalAvaliacaoFornecedor.isPresent()){
                return optionalAvaliacaoFornecedor.get();
            }
        }
        if (Objects.nonNull(assessorId)){
            Optional<Avaliacao> optionalAvaliacaoAssessor = repository.findByAssessorIdAndCasalId(assessorId, casalId);
            if (optionalAvaliacaoAssessor.isPresent()){
                return optionalAvaliacaoAssessor.get();
            }
        }

        return avaliacao;
    }

    private void validateFornecedorAndAssessorIds(Integer fornecedorId, Integer assessorId) {
        if (Objects.nonNull(assessorId) && Objects.nonNull(fornecedorId)){
            throw new IllegalArgumentException("Não é possível criar uma avaliação para um fornecedor e assessor ao mesmo tempo!");
        }
    }

}
