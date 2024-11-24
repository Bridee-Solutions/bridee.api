package com.bridee.api.service;

import com.bridee.api.entity.Assessor;
import com.bridee.api.entity.Casal;
import com.bridee.api.entity.Casamento;
import com.bridee.api.entity.CasamentoAssessorado;
import com.bridee.api.entity.enums.CasamentoStatusEnum;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.exception.UnprocessableEntityException;
import com.bridee.api.repository.CasamentoRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class CasamentoService {

    private final CasamentoRepository repository;
    private final AssessorService assessorService;
    private final CasamentoAssessoradoService casamentoAssessoradoService;

    public Casamento findById(Integer id){
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Casamento não encontrado!"));
    }

    public Casamento save(Casal casal, Integer qtdConvidados, LocalDate dataCasamento,
                          boolean isReservado, String local){
        Casamento casamento = Casamento.builder()
                .nome("%s e %s".formatted(casal.getNome(), casal.getNomeParceiro()))
                .dataFim(dataCasamento)
                .localReservado(BooleanUtils.toBoolean(isReservado))
                .local(local)
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
        int updatedSuccessfully = repository.updateCasamentoMessage(casamentoId, message);
        if (updatedSuccessfully == 0){
            throw new UnprocessableEntityException("Não foi possível atualizar a mensagem dos convites");
        };
    }

    public Assessor vinculateAssessorToWedding(Integer casamentoId, Integer assessorId) {
        Casamento casamento = findById(casamentoId);
        Assessor assessor = assessorService.findById(assessorId);
        CasamentoAssessorado casamentoAssessorado = new CasamentoAssessorado(null, null, casamento, assessor);
        casamentoAssessorado = casamentoAssessoradoService.save(casamentoAssessorado);
        return casamentoAssessorado.getAssessor();
    }
}
