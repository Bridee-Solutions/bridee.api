package com.bridee.api.service;

import com.bridee.api.dto.request.SolicitacaoOrcamentoRequestDto;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.repository.AssessorRepository;
import com.bridee.api.repository.CasalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrcamentoService {

    private final AssessorRepository assessorRepository;
    private final CasalRepository casalRepository;

    public void validateSolicitacaoOrcamento(SolicitacaoOrcamentoRequestDto requestDto){

        String emailAssessorEmpresa = requestDto.getEmailEmpresaAssessor();
        if (!assessorRepository.existsByEmailEmpresa(emailAssessorEmpresa)){
            throw new ResourceNotFoundException("Não existe assessor com esse e-mail");
        }

        String emailCasal = requestDto.getEmailCasal();
        if (!casalRepository.existsByEmail(emailCasal)){
            throw new ResourceNotFoundException("Não existe casal com esse e-mail");
        }
    }
}
