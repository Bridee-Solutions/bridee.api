package com.bridee.api.service;

import com.bridee.api.entity.Casamento;
import com.bridee.api.entity.PedidoAssessoria;
import com.bridee.api.entity.enums.PedidoAssessoriaStatusEnum;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.exception.UnprocessableEntityException;
import com.bridee.api.repository.PedidoAssessoriaRepository;
import com.bridee.api.repository.CasamentoRepository;
import com.bridee.api.repository.specification.PedidoAssessoriaFilter;
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
public class PedidoAssessoriaService {

    private final PedidoAssessoriaRepository repository;

    public Page<Casamento> findCasamentosPendenteByAssessorId(Integer assessorId, Pageable pageable){
        return repository.findAllCasamentoPendente(assessorId, PedidoAssessoriaStatusEnum.PENDENTE_APROVACAO, pageable);
    }

    public PedidoAssessoria findByCasamentoId(Integer casamentoId) {
        return repository.findAssessorByCasamentoId(casamentoId);
    }

    public PedidoAssessoria save(PedidoAssessoria pedidoAssessoria){
        validatePedidoAssessoria(pedidoAssessoria);
        return repository.save(pedidoAssessoria);
    }

    private void validatePedidoAssessoria(PedidoAssessoria pedidoAssessoria){
        Integer assessorId = pedidoAssessoria.getAssessor().getId();
        Integer casamentoId = pedidoAssessoria.getCasamento().getId();
        if (isCasamentoAssessorado(assessorId, casamentoId)){
            throw new UnprocessableEntityException("Casamento já assessorado.");
        }
    }

    public void updatePrecoCasamentoAssessor(Integer assessorId, Integer casamentoId, BigDecimal preco) {
        if (!isCasamentoAssessorado(assessorId, casamentoId)) {
            throw new ResourceNotFoundException("Casamento não assessorado!");
        }
        repository.updatePreco(assessorId, casamentoId, preco);
    }

    public void denyWedding(Integer casamentoId, Integer assessorId) {
        PedidoAssessoria pedidoAssessoria = findCasamentoAssessorado(casamentoId, assessorId);
        denyWedding(pedidoAssessoria);
    }

    private void denyWedding(PedidoAssessoria pedidoAssessoria) {
        validatePendingStatusWedding(pedidoAssessoria);
        pedidoAssessoria.setStatus(PedidoAssessoriaStatusEnum.NAO_ASSESSORADO);
        repository.save(pedidoAssessoria);
    }

    public void acceptWedding(Integer casamentoId, Integer assessorId) {
        PedidoAssessoria pedidoAssessoria = findCasamentoAssessorado(casamentoId, assessorId);
        acceptWedding(pedidoAssessoria);
    }

    private void acceptWedding(PedidoAssessoria pedidoAssessoria) {
        validatePendingStatusWedding(pedidoAssessoria);
        pedidoAssessoria.setStatus(PedidoAssessoriaStatusEnum.ASSESSORADO);
        repository.save(pedidoAssessoria);
    }

    public void removeWeddingAdvise(Integer casamentoId, Integer assessorId) {
        PedidoAssessoria pedidoAssessoria = findCasamentoAssessorado(casamentoId, assessorId);
        removeWeddingAdvise(pedidoAssessoria);
    }

    private void removeWeddingAdvise(PedidoAssessoria pedidoAssessoria) {
        validateAdvisedStatusWedding(pedidoAssessoria);
        pedidoAssessoria.setStatus(PedidoAssessoriaStatusEnum.NAO_ASSESSORADO);
        repository.save(pedidoAssessoria);
    }

    private PedidoAssessoria findCasamentoAssessorado(Integer casamentoId, Integer assessorId){
        Optional<PedidoAssessoria> casamentoAssessoradoOpt = repository.findByCasamentoIdAndAssessorId(casamentoId, assessorId);
        if (casamentoAssessoradoOpt.isEmpty()){
            throw new ResourceNotFoundException("Casamento assessorado não encontrado!");
        }
        return casamentoAssessoradoOpt.get();
    }

    private void validatePendingStatusWedding(PedidoAssessoria pedidoAssessoria){
        if (!pedidoAssessoria.getStatus().equals(PedidoAssessoriaStatusEnum.PENDENTE_APROVACAO)){
            throw new UnprocessableEntityException("Não foi possível realizar a operação, status do casamento inválido!");
        }
    }

    private void validateAdvisedStatusWedding(PedidoAssessoria pedidoAssessoria){
        if (!pedidoAssessoria.getStatus().equals(PedidoAssessoriaStatusEnum.ASSESSORADO)){
            throw new UnprocessableEntityException("Não foi possível realizar a operação, status do casamento inválido!");
        }
    }

    private boolean isCasamentoAssessorado(Integer assessorId, Integer casamentoId){
        return repository.existsByAssessorIdAndCasamentoIdAndStatus(assessorId, casamentoId,
                PedidoAssessoriaStatusEnum.ASSESSORADO);
    }

    public List<Casamento> findCasamentosAssessoradosByAssessorId(Integer assessorId, Integer mes, Integer ano) {
        LocalDate dateToBeFiltered = LocalDate.of(ano, mes, LocalDate.now().getDayOfMonth());
        Specification<PedidoAssessoria> specification = PedidoAssessoriaFilter.findByAssessorId(assessorId)
                .and(PedidoAssessoriaFilter.findByCasamentoStatus(PedidoAssessoriaStatusEnum.ASSESSORADO)
                .and(PedidoAssessoriaFilter.findByDate(dateToBeFiltered)));
        return repository.findAll(specification).stream()
                .map(PedidoAssessoria::getCasamento).toList();
    }
}
