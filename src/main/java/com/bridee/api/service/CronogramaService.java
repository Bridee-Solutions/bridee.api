package com.bridee.api.service;

import com.bridee.api.entity.Atividade;
import com.bridee.api.entity.Cronograma;
import com.bridee.api.exception.ResourceAlreadyExists;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.repository.AtividadeRepository;
import com.bridee.api.repository.CronogramaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CronogramaService {

    private final CronogramaRepository repository;
    private final AtividadeRepository atividadeRepository;
    private final CasamentoService casamentoService;


    public List<Atividade> findAllByCasamentoId(Integer casamentoId) {
        casamentoService.existsById(casamentoId);
        return repository.findAtividadesCronogramaByCasamentoId(casamentoId);
    }

    public Atividade saveAtividade(Atividade atividade, Integer cronogramaId){
        if (atividadeRepository.existsByNome(atividade.getNome())){
            throw new ResourceAlreadyExists("Atividade já cadastrada no cronograma do casamento!");
        }
        Cronograma cronograma = repository.findById(cronogramaId)
                .orElseThrow(() -> new ResourceNotFoundException("Cronograma não encontrado!"));
        atividade = atividadeRepository.save(atividade);
        cronograma.getAtividades().add(atividade);
        repository.save(cronograma);
        return atividade;
    }

    public Atividade updateAtividade(Atividade atividade, Integer idAtividade){
        if (!atividadeRepository.existsById(idAtividade)){
            throw new ResourceNotFoundException("Atividade não encontrada!");
        }
        atividade.setId(idAtividade);
        return atividadeRepository.save(atividade);
    }

    public void deleteAtividadeById(Integer idAtividade){
        if (!atividadeRepository.existsById(idAtividade)){
            throw new ResourceNotFoundException("Atividade não encontrada!");
        }
        atividadeRepository.deleteById(idAtividade);
    }

}
