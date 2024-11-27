package com.bridee.api.service;

import com.bridee.api.entity.Assessor;
import com.bridee.api.entity.Casamento;
import com.bridee.api.entity.CasamentoAssessorado;
import com.bridee.api.entity.enums.CasamentoStatusEnum;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.exception.UnprocessableEntityException;
import com.bridee.api.repository.CasamentoAssessoradoRepository;
import com.bridee.api.repository.CasamentoRepository;
import com.bridee.api.repository.specification.CasamentoAssessoradoFilter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CasamentoAssessoradoService {

    private final CasamentoAssessoradoRepository repository;
    private final CasamentoRepository casamentoRepository;

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

    public void denyWedding(Integer casamentoId, Integer assessorId) {
        CasamentoAssessorado casamentoAssessorado = findCasamentoAssessorado(casamentoId, assessorId);
        Casamento casamento = casamentoAssessorado.getCasamento();
        denyWedding(casamento, casamentoAssessorado);
    }

    private void denyWedding(Casamento casamento, CasamentoAssessorado casamentoAssessorado) {
        validatePendingStatusWedding(casamento);
        casamento.setStatus(CasamentoStatusEnum.NAO_ASSESSORADO);
        casamentoRepository.save(casamento);
        repository.delete(casamentoAssessorado);
    }

    public void acceptWedding(Integer casamentoId, Integer assessorId) {
        CasamentoAssessorado casamentoAssessorado = findCasamentoAssessorado(casamentoId, assessorId);
        Casamento casamento = casamentoAssessorado.getCasamento();
        acceptWedding(casamento);
    }

    private void acceptWedding(Casamento casamento) {
        validatePendingStatusWedding(casamento);
        casamento.setStatus(CasamentoStatusEnum.ASSESSORADO);
        casamentoRepository.save(casamento);
    }

    public void removeWeddingAdvise(Integer casamentoId, Integer assessorId) {
        CasamentoAssessorado casamentoAssessorado = findCasamentoAssessorado(casamentoId, assessorId);
        Casamento casamento = casamentoAssessorado.getCasamento();
        removeWeddingAdvise(casamento, casamentoAssessorado);
    }

    private void removeWeddingAdvise(Casamento casamento, CasamentoAssessorado casamentoAssessorado) {
        validateAdvisedStatusWedding(casamento);
        casamento.setStatus(CasamentoStatusEnum.NAO_ASSESSORADO);
        casamentoRepository.save(casamento);
        repository.delete(casamentoAssessorado);
    }

    private CasamentoAssessorado findCasamentoAssessorado(Integer casamentoId, Integer assessorId){
        Optional<CasamentoAssessorado> casamentoAssessoradoOpt = repository.findByCasamentoIdAndAssessorId(casamentoId, assessorId);
        if (casamentoAssessoradoOpt.isEmpty()){
            throw new ResourceNotFoundException("Casamento assessorado não encontrado!");
        }
        return casamentoAssessoradoOpt.get();
    }

    private void validatePendingStatusWedding(Casamento casamento){
        if (!casamento.getStatus().equals(CasamentoStatusEnum.PENDENTE_APROVACAO)){
            throw new UnprocessableEntityException("Não foi possível realizar a operação, status do casamento inválido!");
        }
    }

    private void validateAdvisedStatusWedding(Casamento casamento){
        if (!casamento.getStatus().equals(CasamentoStatusEnum.ASSESSORADO)){
            throw new UnprocessableEntityException("Não foi possível realizar a operação, status do casamento inválido!");
        }
    }

    public List<Casamento> findCasamentosAssessoradosByAssessorId(Integer assessorId, Integer mes, Integer ano) {
        LocalDate dateToBeFiltered = LocalDate.of(ano, mes, LocalDate.now().getDayOfMonth());
        Specification<CasamentoAssessorado> specification = CasamentoAssessoradoFilter.findByAssessorId(assessorId)
                .and(CasamentoAssessoradoFilter.findByCasamentoStatus(CasamentoStatusEnum.ASSESSORADO)
                .and(CasamentoAssessoradoFilter.findByDate(dateToBeFiltered)));
        return repository.findAll(specification).stream()
                .map(CasamentoAssessorado::getCasamento).toList();
    }
}
