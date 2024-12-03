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
    
    private final TipoCerimoniaRepository tipoCerimoniaRepository;
    
    public List<TipoCerimoniaAssociado> update(List<Integer> tipoCerimoniaAssociado, InformacaoAssociado associado){
        
        List<TipoCerimoniaAssociado> registrosExistentes = repository.findAllByInformacaoAssociadoId(associado.getId());
        
        
        List<Integer> idsExistentes = registrosExistentes.stream()
        .map(registro -> registro.getTipoCerimonia().getId())
        .toList();
        
        List<TipoCerimoniaAssociado> registrosParaDeletar = registrosExistentes.stream()
        .filter(registro -> !tipoCerimoniaAssociado.contains(registro.getTipoCerimonia().getId()))
        .toList();
        
        registrosParaDeletar.forEach(registro -> repository.deleteById(registro.getId()));
        
        List<Integer> idsParaInserir = tipoCerimoniaAssociado.stream()
        .filter(id -> !idsExistentes.contains(id))
        .toList();
        
        List<TipoCerimoniaAssociado> registrosParaInserir = idsParaInserir.stream()
        .map(id -> {
            TipoCerimonia tipoCerimonia = tipoCerimoniaRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("TipoCasamento não encontrado para ID: " + id));
            
            TipoCerimoniaAssociado novoRegistro = new TipoCerimoniaAssociado();
            novoRegistro.setInformacaoAssociado(associado);
            novoRegistro.setTipoCerimonia(tipoCerimonia);
            return novoRegistro;
        })
        .toList();
        
        repository.saveAll(registrosParaInserir);
        
        return repository.findAllByInformacaoAssociadoId(associado.getId());
    }
    
    public List<TipoCerimonia> findAllByInformacaoAssociadoId(InformacaoAssociado associado){   
        List<TipoCerimoniaAssociado> tiposCerimoniaAssociados = repository.findAllByInformacaoAssociadoId(associado.getId());
        
        List<Integer> tipoCerimoniaIds = tiposCerimoniaAssociados.stream()
        .map(tipoCerimoniaAssociado -> tipoCerimoniaAssociado.getTipoCerimonia().getId())
        .toList();
        
        return tipoCerimoniaRepository.findAllById(tipoCerimoniaIds);
    }
}
