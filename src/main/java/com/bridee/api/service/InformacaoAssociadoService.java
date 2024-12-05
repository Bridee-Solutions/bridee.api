package com.bridee.api.service;

import com.bridee.api.dto.request.ImageMetadata;
import com.bridee.api.entity.Assessor;
import org.springframework.stereotype.Service;

import com.bridee.api.entity.InformacaoAssociado;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.repository.InformacaoAssociadoRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InformacaoAssociadoService {

    private final InformacaoAssociadoRepository repository;
    private final AssessorService assessorService;
    private final ImagemService imagemService;
    private final ImagemAssociadoService imagemAssociadoService;

    public InformacaoAssociado save(InformacaoAssociado informacaoAssociado, Integer assessorId){
        assessorService.existsById(assessorId);
        Assessor assessor = new Assessor(assessorId);
        informacaoAssociado.setAssessor(assessor);
        informacaoAssociado.getImagemAssociados().forEach(imagemAssociado -> {
            imagemAssociado.setImagem(imagemService.save(imagemAssociado.getImagem()));
        });
        InformacaoAssociado savedInformacaoAssociado = repository.save(informacaoAssociado);
        informacaoAssociado.getImagemAssociados().forEach(imagemAssociado -> imagemAssociado.setInformacaoAssociado(savedInformacaoAssociado));
        imagemAssociadoService.saveAll(informacaoAssociado.getImagemAssociados());
        return repository.save(informacaoAssociado);
    }

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
