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
        // Busca a entidade existente vinculada ao Assessor
        InformacaoAssociado informacaoExistente = repository.findByAssessorId(id)
            .orElseThrow(() -> new ResourceNotFoundException("Informação de associado não encontrada para o idAssessor: " + id));
    
        // Preserva o ID existente
        info.setId(informacaoExistente.getId());
    
        // Define o assessor existente, caso necessário
        if (info.getAssessor() == null) {
            info.setAssessor(informacaoExistente.getAssessor());
        }
    
        // Salva a entidade atualizada
        return repository.save(info);
    }
}
