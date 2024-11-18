package com.bridee.api.service;

import com.bridee.api.dto.response.AssessorResponseDto;
import com.bridee.api.dto.response.CasalResponseDto;
import com.bridee.api.dto.response.DashboardResponseDto;
import com.bridee.api.dto.response.TarefaResponseDto;
import com.bridee.api.entity.Assessor;
import com.bridee.api.entity.Casal;
import com.bridee.api.entity.Casamento;
import com.bridee.api.entity.Convidado;
import com.bridee.api.entity.Mesa;
import com.bridee.api.entity.Tarefa;
import com.bridee.api.entity.enums.TarefaStatusEnum;
import com.bridee.api.mapper.response.AssessorResponseMapper;
import com.bridee.api.mapper.response.CasalResponseMapper;
import com.bridee.api.mapper.response.FornecedorOrcamentoResponseMapper;
import com.bridee.api.mapper.response.TarefaResponseMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DashboardService {

    private final OrcamentoService orcamentoService;
    private final OrcamentoFornecedorService orcamentoFornecedorService;
    private final CasamentoService casamentoService;
    private final ConvidadoService convidadoService;
    private final MesaService mesaService;
    private final TarefaService tarefaService;
    private final ItemOrcamentoService itemOrcamentoService;
    private final SubcategoriaServicoService subcategoriaServicoService;
    private final FornecedorOrcamentoResponseMapper fornecedorOrcamentoResponseMapper;
    private final AssessorResponseMapper assessorResponseMapper;
    private final TarefaResponseMapper tarefaResponseMapper;
    private final CasalResponseMapper casalResponseMapper;


    //TODO: validar se o usuário realizando a requisição é o casal ou um adm.
    //TODO: otimizar a comunicação com o banco e refatorar o código.
    public DashboardResponseDto buildDashboard(Integer casamentoId){
        Casamento casamento = casamentoService.findById(casamentoId);
        Casal casal = casamento.getCasal();
        CasalResponseDto casalResponseDto =  casalResponseMapper.toDomain(casal);
        Assessor assessor = casamento.getAssessor();

        itemOrcamentoService.findAllByCasalId(casal.getId());
        orcamentoFornecedorService.findByCasalId(casal.getId());
        subcategoriaServicoService.findAll();
        BigDecimal orcamentoTotal = casal.getOrcamentoTotal();
        BigDecimal totalGasto = orcamentoService.calculateTotalOrcamento(casal);
        var orcamentoFornecedoresProjections = orcamentoFornecedorService.findAllOrcamentoFornecedorByCasalId(casal.getId());
        var orcamentoFornecedores = fornecedorOrcamentoResponseMapper.fromProjection(orcamentoFornecedoresProjections);
        AssessorResponseDto assessorResponseDto = assessorResponseMapper.toDomain(assessor);
        List<Mesa> mesasCasamento = mesaService.findAllByCasamentoId(casamentoId);
        Integer totalAssentos = mesasCasamento.size();
        List<Convidado> convidadosCasamento = convidadoService.findByCasamentoIdAndNome(casamentoId, null);
        Optional<List<Convidado>> convidadosWithMesaOpt = convidadoService.convidadosWithMesa(mesasCasamento);
        List<Convidado> convidadosWithMesa = new ArrayList<>();
        if (convidadosWithMesaOpt.isPresent()){
            convidadosWithMesa = convidadosWithMesaOpt.get();
        }
        String totalConvidados = casamento.getTamanhoCasamento();
        LocalDate dataCasamento = casamento.getDataFim();
        String local = casamento.getLocal();

        List<Tarefa> tarefasCasal = tarefaService.findAllByCasalId(casal.getId());

        Integer totalTarefas = tarefasCasal.size();
        Integer totalTarefasCompletas = (int) tarefasCasal.stream().filter(tarefa -> tarefa.getStatus().equals(TarefaStatusEnum.CONCLUIDO)).count();
        Comparator<Tarefa> tarefaComparator = Comparator.comparing(Tarefa::getId);
        tarefasCasal.sort(tarefaComparator::compare);
        List<TarefaResponseDto> last3Tarefas = tarefasCasal.stream().map(tarefaResponseMapper::toDomain).limit(3).toList();

        return DashboardResponseDto.builder()
                .assentosResumo(DashboardResponseDto.DashboardAssentos.builder()
                        .convidadosSentados(convidadosWithMesa.size())
                        .totalMesas(totalAssentos)
                        .totalConvidados(convidadosCasamento.size())
                        .build())
                .tarefas(DashboardResponseDto.DashboardTarefa.builder()
                        .totalConcluidos(totalTarefasCompletas)
                        .totalItens(totalTarefas)
                        .ultimasTarefas(last3Tarefas)
                        .build())
                .casamentoInfo(DashboardResponseDto.DashboardCasamento.builder()
                        .local(local)
                        .dataCasamento(dataCasamento)
                        .quantidadeConvidados(totalConvidados)
                        .casal(casalResponseDto)
                        .build())
                .assessorResponseDto(assessorResponseDto)
                .orcamentoFornecedorResponse(orcamentoFornecedores)
                .orcamento(DashboardResponseDto.DashboardOrcamento.builder()
                        .orcamentoGasto(totalGasto)
                        .orcamentoTotal(orcamentoTotal)
                        .build())
                .build();
    }


}
