package com.bridee.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bridee.api.entity.InformacaoAssociado;
import com.bridee.api.entity.TipoCasamentoAssociado;
import com.bridee.api.entity.TipoCerimonia;
import com.bridee.api.entity.TipoCerimoniaAssociado;
import com.bridee.api.repository.TipoCerimoniaAssociadoRepository;
import com.bridee.api.repository.TipoCerimoniaRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TipoCerimoniaAssociadoService {
    
    private final TipoCerimoniaAssociadoRepository repository;
    
    public List<TipoCerimonia> findAllByInformacaoAssociadoId(InformacaoAssociado associado){   
        return repository.findAllTipoCerimoniaByInformacaoAssociadoId(associado.getId());
    }
}
