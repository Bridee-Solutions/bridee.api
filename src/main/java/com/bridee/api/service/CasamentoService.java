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
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Service
@AllArgsConstructor
@Transactional
public class CasamentoService {

    private final CasamentoRepository repository;
    private final AssessorService assessorService;
    private final OrcamentoFornecedorRepository orcamentoFornecedorRepository;
    private final CustoRepository custoRepository;
    private final PedidoAssessoriaService pedidoAssessoriaService;

    public Casamento findById(Integer id){
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Casamento não encontrado!"));
    }

    public Integer getCasamentoId(Integer casalId){
        int casamentoId = repository.findCasamentoIdByCasalId(casalId);
        existsById(casamentoId);
        return casamentoId;
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
        PedidoAssessoria pedidoAssessoria = generatePedidoAssessoria(casamentoId, assessorId);
        return pedidoAssessoria.getAssessor();
    }

    private PedidoAssessoria generatePedidoAssessoria(Integer casamentoId, Integer assessorId){
        Assessor assessor = assessorService.findById(assessorId);
        Casamento casamento = Casamento.builder()
                .id(casamentoId).build();
        PedidoAssessoria pedidoAssessoria = new PedidoAssessoria(PedidoAssessoriaStatusEnum.NAO_ASSESSORADO,
                casamento, assessor);
        return pedidoAssessoriaService.save(pedidoAssessoria);
    }

    public BigDecimal calculateOrcamento(Integer casamentoId) {
        Casamento casamento = findById(casamentoId);
        Casal casal = casamento.getCasal();
        Long totalPriceItens = custoRepository.totalPriceItens(casal.getId());
        Long totalPriceOrcamento = orcamentoFornecedorRepository.totalOrcamentoFornecedorPrice(casal.getId());
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
}
