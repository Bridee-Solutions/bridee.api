package com.bridee.api.service;

import com.bridee.api.entity.TipoCasamento;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.repository.AssessorRepository;
import com.bridee.api.repository.FornecedorRepository;
import com.bridee.api.repository.TipoCasamentoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TipoCasamentoService {

    private final TipoCasamentoRepository repository;
    private final AssessorRepository assessorRepository;
    private final FornecedorRepository fornecedorRepository;

    public List<String> findNomeTiposCasamentoFornecedor(Integer id) {
        if (!fornecedorRepository.existsById(id)){
            log.error("FORNECEDOR: fornecedor não encontrado para o fornecedor de id {}", id);
            throw new ResourceNotFoundException("Fornecedor não encontrado!");
        }
        log.info("FORNECEDOR: buscando informações do tipo de casamento para o fornecedor com id: {}", id);
        return repository.findByInformacaoFornecedorId(id);
    }

    public List<String> findNomeTiposCasamentoAssessor(Integer id){
        if (!assessorRepository.existsById(id)){
            log.error("Assessor não encontrado para o id {}", id);
            throw new ResourceNotFoundException("Assessor não encontrado!");
        }
        log.info("ASSESSOR: buscando os tipos de casamento atendidos pelo assessor com id: {}", id);
        return repository.findByInformacaoAssessorId(id);
    }
}
