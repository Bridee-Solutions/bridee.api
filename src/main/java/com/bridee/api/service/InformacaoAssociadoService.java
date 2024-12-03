package com.bridee.api.service;

import org.springframework.stereotype.Service;

import com.bridee.api.entity.InformacaoAssociado;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.repository.InformacaoAssociadoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InformacaoAssociadoService {

    private final InformacaoAssociadoRepository repository;

    public InformacaoAssociado update(InformacaoAssociado info, Integer id) {
        InformacaoAssociado informacaoExistente = repository.findByAssessorId(id)
            .orElseThrow(() -> new ResourceNotFoundException("Informação de associado não encontrada para o idAssessor: " + id));
    
        info.setId(informacaoExistente.getId());
    
        if (info.getAssessor() == null) {
            info.setAssessor(informacaoExistente.getAssessor());
        }
    
        return repository.save(info);
    }

    public InformacaoAssociado findByAssessorId(Integer id) {
        return repository.findByAssessorId(id)
        .orElseThrow(() -> new ResourceNotFoundException("Informação de associado não encontrada para o idAssessor: " + id));
    }
}
