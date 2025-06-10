package com.bridee.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bridee.api.entity.FormaPagamento;
import com.bridee.api.entity.FormaPagamentoAssociado;
import com.bridee.api.entity.InformacaoAssociado;
import com.bridee.api.repository.FormaPagamentoAssociadoRepository;
import com.bridee.api.repository.FormaPagamentoRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FormaPagamentoAssociadoService {
    
    private final FormaPagamentoAssociadoRepository repository;
    
    public List<FormaPagamento> findAllByInformacaoAssociadoId(InformacaoAssociado associado) {
        return repository.findAllFormaPagamentoByInformacaoAssociadoId(associado.getId());
    }
}
