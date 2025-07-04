package com.bridee.api.service;

import com.bridee.api.entity.Assessor;
import com.bridee.api.entity.Casal;
import com.bridee.api.entity.Casamento;
import com.bridee.api.entity.PedidoAssessoria;
import com.bridee.api.entity.enums.PedidoAssessoriaStatusEnum;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.exception.UnprocessableEntityException;
import com.bridee.api.repository.CasamentoRepository;
import com.bridee.api.repository.CustoRepository;
import com.bridee.api.repository.OrcamentoFornecedorRepository;
import com.bridee.api.repository.projection.casamento.CasamentoDateProjection;
import com.bridee.api.utils.PatchHelper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class CasamentoService {

    private final CasamentoRepository repository;
    private final OrcamentoFornecedorRepository orcamentoFornecedorRepository;
    private final CustoRepository custoRepository;
    private final PedidoAssessoriaService pedidoAssessoriaService;
    private final PatchHelper patchHelper;

    public Casamento findById(Integer id){
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Casamento não encontrado!"));
    }

    public CasamentoDateProjection findDataFimByCasamentoId(Integer id){
        return repository.findDataFimByCasamentoId(id);
    }

    public Integer getCasamentoId(Integer casalId){
        Integer casamentoId = repository.findCasamentoIdByCasalId(casalId);
        existsById(casamentoId);
        return casamentoId;
    }

    public Integer getWeddingIdFromCoupleEmail(String casalEmail){
        return repository.findCasamentoIdByCoupleEmail(casalEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Casamento não encontrado!"));
    }

    public Casamento save(Casal casal, Integer qtdConvidados, LocalDate dataCasamento,
                          boolean isReservado, String local, String tamanhoCasamento){
        Casamento casamento = Casamento.builder()
                .nome("%s e %s".formatted(casal.getNome(), casal.getNomeParceiro()))
                .dataFim(dataCasamento)
                .localReservado(BooleanUtils.toBoolean(isReservado))
                .local(local)
                .tamanhoCasamento(tamanhoCasamento)
                .totalConvidados(qtdConvidados)
                .casal(casal)
                .build();

        return repository.save(casamento);
    }

    public void existsById(Integer casamentoId){
        if (!repository.existsById(casamentoId)){
            log.error("CASAMENTO: casamento não encontrado com id {}", casamentoId);
            throw new ResourceNotFoundException("Casamento não encontrado!");
        }
    }

    public void updateMessage(Integer casamentoId, String message){
        existsById(casamentoId);
        int totalUpdates = repository.updateCasamentoMessage(casamentoId, message);
        if (totalUpdates == 0){
            throw new UnprocessableEntityException("Não foi possível atualizar a mensagem dos convites");
        };
    }

    public Assessor vinculateAssessorToWedding(Integer casamentoId, Integer assessorId) {
        PedidoAssessoria pedidoAssessoria = createPedidoAssessoria(casamentoId, assessorId);
        return pedidoAssessoria.getAssessor();
    }

    private PedidoAssessoria createPedidoAssessoria(Integer casamentoId, Integer assessorId){
        PedidoAssessoria pedidoAssessoria = buildPedidoAssessoria(casamentoId, assessorId);
        return pedidoAssessoriaService.save(pedidoAssessoria);
    }

    private PedidoAssessoria buildPedidoAssessoria(Integer casamentoId, Integer assessorId){
        Assessor assessor = new Assessor(assessorId);
        Casamento casamento = Casamento.builder()
                .id(casamentoId)
                .build();
        return PedidoAssessoria.builder()
                .assessor(assessor)
                .casamento(casamento)
                .status(PedidoAssessoriaStatusEnum.NAO_ASSESSORADO)
                .build();
    }

    public BigDecimal calculateOrcamento(Integer casalId) {
        Long totalPriceItens = custoRepository.totalPriceItens(casalId);
        Long totalPriceOrcamento = orcamentoFornecedorRepository.totalOrcamentoFornecedorPrice(casalId);
        return calculateOrcamento(totalPriceItens, totalPriceOrcamento);
    }

    private BigDecimal calculateOrcamento(Long totalPriceItens, Long totalPriceOrcamento){
        if (Objects.nonNull(totalPriceItens) && Objects.nonNull(totalPriceOrcamento)) {
            return new BigDecimal(totalPriceItens + totalPriceOrcamento);
        }
        if (Objects.nonNull(totalPriceItens)){
            return new BigDecimal(totalPriceItens);
        }
        if (Objects.nonNull(totalPriceOrcamento)){
            return new BigDecimal(totalPriceOrcamento);
        }
        return new BigDecimal("0");
    }

    public void denyWedding(Integer casamentoId, Integer assessorId) {
        pedidoAssessoriaService.denyWedding(casamentoId, assessorId);
    }

    public void acceptWedding(Integer casamentoId, Integer assessorId) {
        pedidoAssessoriaService.acceptWedding(casamentoId, assessorId);
    }

    public void removeWeddingAdvise(Integer casamentoId, Integer assessorId) {
        pedidoAssessoriaService.removeWeddingAdvise(casamentoId, assessorId);
    }

    public Casamento updateCasamento(Casamento casamento, Integer casamentoId) {
        Casamento casamentoToBeUpdated = findById(casamentoId);
        patchHelper.mergeNonNull(casamento, casamentoToBeUpdated);
        return repository.save(casamentoToBeUpdated);
    }
}
