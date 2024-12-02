package com.bridee.api.service;

import java.util.ArrayList;
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
    
    private final FormaPagamentoRepository formaPagamentoRepository; 

    public List<FormaPagamentoAssociado> update(List<Integer> formaPagamentoAssociado, InformacaoAssociado associado){
        List<FormaPagamentoAssociado> registrosExistentes = repository.findAllByInformacaoAssociadoId(associado.getId());
        
        
        List<Integer> idsExistentes = registrosExistentes.stream()
        .map(registro -> registro.getFormaPagamento().getId())
        .toList();
        
        List<FormaPagamentoAssociado> registrosParaDeletar = registrosExistentes.stream()
        .filter(registro -> !formaPagamentoAssociado.contains(registro.getFormaPagamento().getId()))
        .toList();
        
        registrosParaDeletar.forEach(registro -> repository.deleteById(registro.getId()));
        
        List<Integer> idsParaInserir = formaPagamentoAssociado.stream()
        .filter(id -> !idsExistentes.contains(id))
        .toList();
        
        List<FormaPagamentoAssociado> registrosParaInserir = idsParaInserir.stream()
        .map(id -> {
            FormaPagamento formaPagamento = formaPagamentoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("FormaPagamento não encontrado para ID: " + id));
            
            FormaPagamentoAssociado novoRegistro = new FormaPagamentoAssociado();
            novoRegistro.setInformacaoAssociado(associado);
            novoRegistro.setFormaPagamento(formaPagamento);;
            return novoRegistro;
        })
        .toList();

        repository.saveAll(registrosParaInserir);
        
        return repository.findAllByInformacaoAssociadoId(associado.getId());
    }
}
