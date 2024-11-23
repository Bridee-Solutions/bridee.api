package com.bridee.api.service;

import com.bridee.api.entity.Assessor;
import com.bridee.api.entity.Casamento;
import com.bridee.api.entity.CasamentoAssessorado;
import com.bridee.api.entity.enums.CasamentoStatusEnum;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.repository.CasamentoAssessoradoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CasamentoAssessoradoService {

    private final CasamentoAssessoradoRepository repository;

    public Page<Casamento> findCasamentosPendenteByAssessorId(Integer assessorId, Pageable pageable){
        return repository.findAllCasamentoPendente(assessorId, CasamentoStatusEnum.PENDENTE_APROVACAO, pageable);
    }

    public CasamentoAssessorado findByCasamentoId(Integer casamentoId) {
        return repository.findAssessorByCasamentoId(casamentoId);
    }

    public CasamentoAssessorado save(CasamentoAssessorado casamentoAssessorado){
        Integer casamentoId = casamentoAssessorado.getCasamento().getId();
        Optional<CasamentoAssessorado> casamentoAssessoradoOpt = repository.findByCasamentoId(casamentoId);
        casamentoAssessoradoOpt.ifPresent(repository::delete);
        return repository.save(casamentoAssessorado);
    }

    public void updatePrecoCasamentoAssessor(Integer assessorId, Integer casamentoId, BigDecimal preco) {
        if (!repository.existsByAssessorIdAndCasamentoId(assessorId, casamentoId)) {
            throw new ResourceNotFoundException("Casamento não assessorado!");
        }
        repository.updatePreco(assessorId, casamentoId, preco);
    }
}
