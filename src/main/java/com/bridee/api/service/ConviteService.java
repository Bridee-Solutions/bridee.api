package com.bridee.api.service;

import com.bridee.api.entity.Convite;
import com.bridee.api.repository.ConviteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConviteService {

    private final ConviteRepository repository;

    public List<Convite> findAllByCasamentoId(Integer casamentoId){
        //TODO: validar existência do casamento.
        return repository.findAllByCasamentoId(casamentoId);
    }

}
