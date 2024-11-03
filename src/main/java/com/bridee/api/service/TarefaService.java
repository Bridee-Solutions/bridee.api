package com.bridee.api.service;

import com.bridee.api.entity.Casal;
import com.bridee.api.entity.Casamento;
import com.bridee.api.entity.Tarefa;
import com.bridee.api.entity.TarefaCasal;
import com.bridee.api.exception.ResourceAlreadyExists;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.repository.TarefaCasalRepository;
import com.bridee.api.repository.TarefaRepository;
import com.bridee.api.repository.specification.TarefaFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class TarefaService {

    private final TarefaRepository repository;
    private final CasamentoService casamentoService;
    private final TarefaCasalRepository tarefaCasalRepository;

    public Page<Tarefa> findAllByCasamentoId(Integer casamentoId, Map<String, Object> params, Pageable pageable){
        Casamento casamento = casamentoService.findById(casamentoId);
        Casal casal = casamento.getCasal();
        params.put("casalId", casal.getId());

        TarefaFilter tarefaFilter = new TarefaFilter();
        tarefaFilter.buildFilter(params);

        return repository.findAll(tarefaFilter, pageable);
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

    public Tarefa update(Tarefa tarefa, Integer id){
        if (!repository.existsById(id)){
            throw new ResourceNotFoundException("Tarefa não encontrada");
        }
        tarefa.setId(id);
        return repository.save(tarefa);
    }

    public void deleteById(Integer id){
        if (!repository.existsById(id)){
            throw new ResourceNotFoundException("Tarefa não encontrada");
        }
        repository.deleteById(id);
    }

}
