package com.bridee.api.service;

import com.bridee.api.entity.Casal;
import com.bridee.api.entity.Casamento;
import com.bridee.api.entity.Tarefa;
import com.bridee.api.entity.TarefaCasal;
import com.bridee.api.exception.ResourceAlreadyExists;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.repository.projection.casamento.CasamentoDateProjection;
import com.bridee.api.repository.TarefaCasalRepository;
import com.bridee.api.repository.TarefaRepository;
import com.bridee.api.repository.specification.TarefaFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class TarefaService {

    private final TarefaRepository repository;
    private final CasamentoService casamentoService;
    private final TarefaCasalRepository tarefaCasalRepository;

    @Transactional(readOnly = true)
    public List<Tarefa> findAllByCasalId(Integer casamentoId, Map<String, Object> params){
        Casamento casamento = casamentoService.findById(casamentoId);
        Casal casal = casamento.getCasal();

        TarefaFilter tarefaFilter = buildTarefaFilter(casal.getId(), params);
        return repository.findAll(tarefaFilter);
    }

    private TarefaFilter buildTarefaFilter(Integer casalId, Map<String, Object> params){
        String years = buildCasalTaskYearsFilter(casalId);
        params.put("casalId", casalId);
        params.put("anos", years);
        return new TarefaFilter()
                .buildFilter(params);
    }

    private String buildCasalTaskYearsFilter(Integer id) {
        List<String> years = repository.findAllYearsFromTasks(id)
                .stream().map(String::valueOf).toList();
        return String.join(",", years);
    }

    public List<Tarefa> findAllByCasalId(Integer casalId){
        Map<String, Object> filterAttributes = new HashMap<>();
        filterAttributes.put("casalId", casalId);

        TarefaFilter tarefaFilter = new TarefaFilter()
                .buildFilter(filterAttributes);
        return repository.findAll(tarefaFilter);
    }

    public Tarefa save(Integer casamentoId, Tarefa tarefa){
        Casamento casamento = casamentoService.findById(casamentoId);
        Casal casal = casamento.getCasal();
        if (tarefaCasalRepository.existsByTarefaNomeAndCasalId(tarefa.getNome(), casal.getId())){
            throw new ResourceAlreadyExists("Tarefa já cadastrada no casamento!");
        }

        tarefa.setMesesAnteriores(casamento.getDataFim().getMonthValue() - tarefa.getDataLimite().getMonthValue());
        tarefa = repository.save(tarefa);
        TarefaCasal tarefaCasal = new TarefaCasal(null, tarefa, casal);
        tarefaCasalRepository.save(tarefaCasal);

        return tarefa;
    }

    public Tarefa update(Tarefa tarefa, Integer tarefaId, Integer casamentoId){
        if (!repository.existsById(tarefaId)){
            throw new ResourceNotFoundException("Tarefa não encontrada");
        }
        CasamentoDateProjection result = casamentoService.findDataFimByCasamentoId(casamentoId);
        tarefa.setMesesAnteriores(result.getDataFim().getMonthValue() - tarefa.getDataLimite().getMonthValue());
        tarefa.setId(tarefaId);
        return repository.save(tarefa);
    }

    public void deleteById(Integer id){
        if (!repository.existsById(id)){
            throw new ResourceNotFoundException("Tarefa não encontrada");
        }
        repository.deleteById(id);
    }

}
