package com.bridee.api.service;

import com.bridee.api.entity.Casal;
import com.bridee.api.entity.Casamento;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.repository.CasamentoRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class CasamentoService {

    private final CasamentoRepository repository;

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

}
