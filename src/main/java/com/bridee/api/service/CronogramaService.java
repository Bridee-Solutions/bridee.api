package com.bridee.api.service;

import com.bridee.api.entity.Atividade;
import com.bridee.api.entity.Cronograma;
import com.bridee.api.exception.ResourceAlreadyExists;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.repository.AtividadeRepository;
import com.bridee.api.repository.CronogramaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CronogramaService {

    private final CronogramaRepository repository;
    private final AtividadeRepository atividadeRepository;

    public List<Atividade> findAllByCasamentoId(Integer casamentoId) {
        Cronograma cronogramaCasamento = repository.findCronogramaByCasamentoId(casamentoId);
        return cronogramaCasamento.getAtividades();
    }

    public Cronograma save(Cronograma cronograma){
        if(repository.existsByCasamentoId(cronograma.getCasamento().getId())){
            throw new ResourceAlreadyExists("Já existe um cronograma para esse casamento");
        }
        return repository.save(cronograma);
    }

    public Atividade saveAtividade(Atividade atividade, Integer cronogramaId){
        if (repository.existsAtividadeByNomeInCronograma(atividade.getNome(), cronogramaId)){
            log.error("ATIVIDADE: atividade já existe para o cronograma de id: {}", cronogramaId);
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
            log.error("ATIVIDADE: atividade não  encontrada com id: {}", idAtividade);
            throw new ResourceNotFoundException("Atividade não encontrada!");
        }
        atividade.setId(idAtividade);
        return atividadeRepository.save(atividade);
    }

    public void deleteAtividadeById(Integer idAtividade){
        if (!atividadeRepository.existsById(idAtividade)){
            log.error("ATIVIDADE: atividade não  encontrada com id: {}", idAtividade);
            throw new ResourceNotFoundException("Atividade não encontrada!");
        }
        atividadeRepository.deleteById(idAtividade);
    }

}
