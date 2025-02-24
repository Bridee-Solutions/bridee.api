package com.bridee.api.service;

import com.bridee.api.dto.response.AssessorResponseDto;
import com.bridee.api.dto.response.CasalResponseDto;
import com.bridee.api.dto.response.DashboardResponseDto;
import com.bridee.api.dto.response.TarefaResponseDto;
import com.bridee.api.entity.Assessor;
import com.bridee.api.entity.Casal;
import com.bridee.api.entity.Casamento;
import com.bridee.api.entity.PedidoAssessoria;
import com.bridee.api.entity.Convidado;
import com.bridee.api.entity.Mesa;
import com.bridee.api.entity.Tarefa;
import com.bridee.api.entity.enums.TarefaStatusEnum;
import com.bridee.api.mapper.response.AssessorResponseMapper;
import com.bridee.api.mapper.response.CasalResponseMapper;
import com.bridee.api.mapper.response.FornecedorOrcamentoResponseMapper;
import com.bridee.api.mapper.response.TarefaResponseMapper;
import com.bridee.api.repository.projection.orcamento.OrcamentoProjection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DashboardService {

    private final OrcamentoService orcamentoService;
    private final CasamentoService casamentoService;
    private final ConvidadoService convidadoService;
    private final MesaService mesaService;
    private final TarefaService tarefaService;
    private final PedidoAssessoriaService pedidoAssessoriaService;
    private final ImagemCasalService imagemCasalService;
    private final FornecedorOrcamentoResponseMapper fornecedorOrcamentoResponseMapper;
    private final AssessorResponseMapper assessorResponseMapper;
    private final TarefaResponseMapper tarefaResponseMapper;
    private final CasalResponseMapper casalResponseMapper;

    @Transactional(readOnly = true)
    public DashboardResponseDto buildDashboard(Integer casamentoId){
        Casamento casamento = casamentoService.findById(casamentoId);
        Casal casal = casamento.getCasal();

        OrcamentoProjection orcamentoProjection = orcamentoService.findCasamentoOrcamento(casamentoId);
        var orcamentoFornecedores = fornecedorOrcamentoResponseMapper.fromProjection(orcamentoProjection.getOrcamentoFornecedores());

        return DashboardResponseDto.builder()
                .assentosResumo(buildDashboardAssentos(casamentoId))
                .tarefas(buildDashboardTarefas(casal.getId()))
                .casamentoInfo(buildDashboardCasamento(casamento, casal))
                .assessorResponseDto(buildDashboardAssessor(casamentoId))
                .orcamentoFornecedorResponse(orcamentoFornecedores)
                .orcamento(buildDashboardOrcamento(orcamentoProjection))
                .build();
    }

    private DashboardResponseDto.DashboardAssentos buildDashboardAssentos(Integer casamentoId){
        log.info("DASHBOARD: construindo as informações de assentos do casamento {}", casamentoId);
        List<Mesa> mesasCasamento = mesaService.findAllByCasamentoId(casamentoId);
        Integer totalAssentos = mesasCasamento.size();
        List<Convidado> convidadosCasamento = convidadoService.findByCasamentoIdAndNome(casamentoId, null);
        List<Convidado> convidadosWithMesa = convidadoService.convidadosWithMesa(mesasCasamento);
        log.debug("DASHBOARD: total de assentos {}, total de convidados {}, total de convidados com mesa {}",
                totalAssentos, convidadosCasamento, convidadosWithMesa);
        return DashboardResponseDto.DashboardAssentos.builder()
                .convidadosSentados(convidadosWithMesa.size())
                .totalMesas(totalAssentos)
                .totalConvidados(convidadosCasamento.size())
                .build();
    }

    private DashboardResponseDto.DashboardTarefa buildDashboardTarefas(Integer casalId){
        log.info("DASHBOARD: construindo as informações das tarefas do casal {}", casalId);
        List<Tarefa> tarefasCasal = tarefaService.findAllByCasalId(casalId);
        Integer totalTarefas = tarefasCasal.size();
        Integer totalTarefasCompletas = (int) tarefasCasal.stream().filter(tarefa -> tarefa.getStatus().equals(TarefaStatusEnum.CONCLUIDO)).count();
        tarefasCasal.sort(Comparator.comparing(Tarefa::getId));
        List<TarefaResponseDto> last3Tarefas = tarefasCasal.stream().map(tarefaResponseMapper::toDomain).limit(3).toList();
        log.debug("DASHBOARD: total de tarefas {}, total de tarefas completas {}",
                totalTarefas, totalTarefasCompletas);
        return DashboardResponseDto.DashboardTarefa.builder()
                .totalConcluidos(totalTarefasCompletas)
                .totalItens(totalTarefas)
                .ultimasTarefas(last3Tarefas)
                .build();
    }

    private DashboardResponseDto.DashboardCasamento buildDashboardCasamento(Casamento casamento, Casal casal){
        log.info("DASHBOARD: construindo informações do casamento");

        CasalResponseDto casalResponseDto =  casalResponseMapper.toDomain(casal);
        String totalConvidados = casamento.getTamanhoCasamento();
        LocalDate dataCasamento = casamento.getDataFim();
        String local = casamento.getLocal();
        String casalImage = imagemCasalService.casalImage64Encoded(casal.getId());

        log.debug("DASHBOARD: total convidados {}, dataCasamento {}, local {}, para o casamento {}",
                totalConvidados, dataCasamento, local, casamento.getId());

        return DashboardResponseDto.DashboardCasamento.builder()
                .local(local)
                .dataCasamento(dataCasamento)
                .quantidadeConvidados(totalConvidados)
                .casal(casalResponseDto)
                .image(casalImage)
                .build();
    }

    private DashboardResponseDto.DashboardAssessor buildDashboardAssessor(Integer casamentoId){
        log.info("DASHBOARD: construindo as informações de assessor.");
        PedidoAssessoria pedidoAssessoria = pedidoAssessoriaService.findPedidoAssessorado(casamentoId);
        BigDecimal precoAssessor = Objects.nonNull(pedidoAssessoria) ? pedidoAssessoria.getPreco() : null;
        Assessor assessor = Objects.nonNull(pedidoAssessoria) ? pedidoAssessoria.getAssessor() : null;
        AssessorResponseDto assessorResponseDto = assessorResponseMapper.toDomain(assessor);

        return DashboardResponseDto.DashboardAssessor.builder()
                .assessor(assessorResponseDto)
                .preco(precoAssessor)
                .build();
    }

    private DashboardResponseDto.DashboardOrcamento buildDashboardOrcamento(OrcamentoProjection orcamento){
        log.info("DASHBOARD: construindo as informações do orçamento, total gasto {}, orcamento total {}",
                orcamento.getOrcamentoGasto(), orcamento.getOrcamentoTotal());
        return DashboardResponseDto.DashboardOrcamento.builder()
                .orcamentoGasto(orcamento.getOrcamentoGasto())
                .orcamentoTotal(orcamento.getOrcamentoTotal())
                .build();
    }

}
