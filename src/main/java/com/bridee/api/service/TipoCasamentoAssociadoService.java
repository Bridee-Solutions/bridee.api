package com.bridee.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bridee.api.entity.InformacaoAssociado;
import com.bridee.api.entity.TipoCasamento;
import com.bridee.api.entity.TipoCasamentoAssociado;
import com.bridee.api.repository.TipoCasamentoAssociadoRepository;
import com.bridee.api.repository.TipoCasamentoRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TipoCasamentoAssociadoService {
    
    private final TipoCasamentoAssociadoRepository repository;
    
    private final TipoCasamentoRepository tipoCasamentoRepository;
    
    public List<TipoCasamentoAssociado> update(List<Integer> tipoCasamentoAssociado, InformacaoAssociado associado){
        
        List<TipoCasamentoAssociado> registrosExistentes = repository.findAllByInformacaoAssociadoId(associado.getId());
        
        
        List<Integer> idsExistentes = registrosExistentes.stream()
        .map(registro -> registro.getTipoCasamento().getId())
        .toList();
        
        List<TipoCasamentoAssociado> registrosParaDeletar = registrosExistentes.stream()
        .filter(registro -> !tipoCasamentoAssociado.contains(registro.getTipoCasamento().getId()))
        .toList();
        
        registrosParaDeletar.forEach(registro -> repository.deleteById(registro.getId()));
        
        List<Integer> idsParaInserir = tipoCasamentoAssociado.stream()
        .filter(id -> !idsExistentes.contains(id))
        .toList();
        
        List<TipoCasamentoAssociado> registrosParaInserir = idsParaInserir.stream()
        .map(id -> {
            TipoCasamento tipoCasamento = tipoCasamentoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("TipoCasamento não encontrado para ID: " + id));
            
            TipoCasamentoAssociado novoRegistro = new TipoCasamentoAssociado();
            novoRegistro.setInformacaoAssociado(associado);
            novoRegistro.setTipoCasamento(tipoCasamento);
            return novoRegistro;
        })
        .toList();
        
        repository.saveAll(registrosParaInserir);
        
        return repository.findAllByInformacaoAssociadoId(associado.getId());
    }
    
    public List<TipoCasamento> findAllByInformacaoAssociadoId(InformacaoAssociado associado) {
        List<TipoCasamentoAssociado> tiposCasamentoAssociados = repository.findAllByInformacaoAssociadoId(associado.getId());
        
        List<Integer> tipoCasamentoIds = tiposCasamentoAssociados.stream()
        .map(tipoCasamentoAssociado -> tipoCasamentoAssociado.getTipoCasamento().getId())
        .toList();

        return tipoCasamentoRepository.findAllById(tipoCasamentoIds);
    }
}
