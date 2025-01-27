package com.bridee.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bridee.api.entity.InformacaoAssociado;
import com.bridee.api.entity.TipoCasamento;
import com.bridee.api.repository.TipoCasamentoAssociadoRepository;
import com.bridee.api.repository.TipoCasamentoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TipoCasamentoAssociadoService {
    
    private final TipoCasamentoAssociadoRepository repository;
    
    public List<TipoCasamento> findAllByInformacaoAssociadoId(InformacaoAssociado associado) {
        return repository.findAllTipoCasamentoByInformacaoAssociadoId(associado.getId());
    }
}
