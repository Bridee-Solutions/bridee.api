package com.bridee.api.service;

import com.bridee.api.entity.Casamento;
import com.bridee.api.entity.PedidoAssessoria;
import com.bridee.api.entity.enums.PedidoAssessoriaStatusEnum;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.exception.UnprocessableEntityException;
import com.bridee.api.repository.PedidoAssessoriaRepository;
import com.bridee.api.repository.specification.PedidoAssessoriaFilter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PedidoAssessoriaService {

    private final PedidoAssessoriaRepository repository;

    public Page<Casamento> findCasamentosPendenteByAssessorId(Integer assessorId, Pageable pageable){
        return repository.findAllCasamentoPendente(assessorId, PedidoAssessoriaStatusEnum.PENDENTE_APROVACAO, pageable);
    }

    //TODO REFATORAR
    public PedidoAssessoria findPedidoAssessorado(Integer casamentoId) {
        PedidoAssessoria pedidoAssessorado = repository.findPedidoByStatus(casamentoId, PedidoAssessoriaStatusEnum.ASSESSORADO).orElse(null);
        if (Objects.nonNull(pedidoAssessorado)){
            return pedidoAssessorado;
        }
        var pedidosPendentes = repository.findAllPedidosCasamentoByStatus(casamentoId, PedidoAssessoriaStatusEnum.PENDENTE_APROVACAO);
        pedidosPendentes.sort(Comparator.comparing(PedidoAssessoria::getId));
        return pedidosPendentes.get(0);
    }

    public PedidoAssessoria save(PedidoAssessoria pedidoAssessoria){
        pedidoAssessoria = validatePedidoAssessoria(pedidoAssessoria);
        pedidoAssessoria.setStatus(PedidoAssessoriaStatusEnum.PENDENTE_APROVACAO);
        return repository.save(pedidoAssessoria);
    }

    private PedidoAssessoria validatePedidoAssessoria(PedidoAssessoria pedidoAssessoria){
        Integer assessorId = pedidoAssessoria.getAssessor().getId();
        Integer casamentoId = pedidoAssessoria.getCasamento().getId();
        Optional<PedidoAssessoria> pedidoAssessoriaOptional = repository.findByCasamentoAndAssessor(casamentoId, assessorId);
        if (pedidoAssessoriaOptional.isEmpty()){
            return pedidoAssessoria;
        }
        pedidoAssessoria = pedidoAssessoriaOptional.get();
        validatePedidoAssessoriaStatus(pedidoAssessoria);
        return pedidoAssessoria;
    }

    private void validatePedidoAssessoriaStatus(PedidoAssessoria pedidoAssessoria){
        if (pedidoAssessoria.getStatus().equals(PedidoAssessoriaStatusEnum.ASSESSORADO)){
            throw new UnprocessableEntityException("Casamento já assessorado!");
        }
        if (pedidoAssessoria.getStatus().equals(PedidoAssessoriaStatusEnum.PENDENTE_APROVACAO)){
            throw new UnprocessableEntityException("Casamento em análise pelo assessor");
        }
    }

    public void updatePrecoCasamentoAssessor(Integer assessorId, Integer casamentoId, BigDecimal preco) {
//        if (!isCasamentoAssessorado(assessorId, casamentoId)) {
//            throw new ResourceNotFoundException("Casamento não assessorado!");
//        }
        repository.updatePreco(assessorId, casamentoId, preco);
    }

    public void denyWedding(Integer casamentoId, Integer assessorId) {
        PedidoAssessoria pedidoAssessoria = findCasamentoPendente(casamentoId, assessorId);
        pedidoAssessoria.setStatus(PedidoAssessoriaStatusEnum.NAO_ASSESSORADO);
        repository.save(pedidoAssessoria);
    }

    public void acceptWedding(Integer casamentoId, Integer assessorId) {
        PedidoAssessoria pedidoAssessoria = findCasamentoPendente(casamentoId, assessorId);
        pedidoAssessoria.setStatus(PedidoAssessoriaStatusEnum.ASSESSORADO);
        repository.save(pedidoAssessoria);
    }

    public void removeWeddingAdvise(Integer casamentoId, Integer assessorId) {
        PedidoAssessoria pedidoAssessoria = findCasamentoAssessorado(casamentoId, assessorId);
        pedidoAssessoria.setStatus(PedidoAssessoriaStatusEnum.NAO_ASSESSORADO);
        repository.save(pedidoAssessoria);
    }

    private PedidoAssessoria findCasamentoAssessorado(Integer casamentoId, Integer assessorId){
        Optional<PedidoAssessoria> casamentoAssessoradoOpt = repository
                .findPedidoCasamentoByStatus(casamentoId, assessorId, PedidoAssessoriaStatusEnum.ASSESSORADO);
        if (casamentoAssessoradoOpt.isEmpty()){
            throw new ResourceNotFoundException("Casamento não assessorado");
        }
        return casamentoAssessoradoOpt.get();
    }

    private PedidoAssessoria findCasamentoPendente(Integer casamentoId, Integer assessorId){
        Optional<PedidoAssessoria> casamentoAssessoradoOpt = repository
                .findPedidoCasamentoByStatus(casamentoId, assessorId, PedidoAssessoriaStatusEnum.PENDENTE_APROVACAO);
        if (casamentoAssessoradoOpt.isEmpty()){
            throw new ResourceNotFoundException("Casamento Pendente de aprovacao não encontrado!");
        }
        return casamentoAssessoradoOpt.get();
    }

    private boolean isCasamentoAssessorado(Integer assessorId, Integer casamentoId){
        return repository.existsByAssessorIdAndCasamentoIdAndStatus(assessorId, casamentoId,
                PedidoAssessoriaStatusEnum.ASSESSORADO);
    }

    public List<Casamento> findCasamentosAssessorados(Integer assessorId, Integer ano) {
        Specification<PedidoAssessoria> specification = PedidoAssessoriaFilter.findByAssessorId(assessorId)
                .and(PedidoAssessoriaFilter.findByPedidoAssessoradoStatus(PedidoAssessoriaStatusEnum.ASSESSORADO)
                .and(PedidoAssessoriaFilter.findByDate(ano)));
        return repository.findAll(specification).stream()
                .map(PedidoAssessoria::getCasamento).toList();
    }
}
