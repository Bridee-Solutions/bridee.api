package com.bridee.api.service;

import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.repository.AssessorRepository;
import com.bridee.api.repository.FormaPagamentoRepository;
import com.bridee.api.repository.FornecedorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FormaPagamentoService {

    private final FormaPagamentoRepository repository;
    private final FornecedorRepository fornecedorRepository;
    private final AssessorRepository assessorRepository;

    public List<String> findNomeFormasPagamentoFornecedor(Integer fornecedorId){
        if (!fornecedorRepository.existsById(fornecedorId)){
            log.error("FORNECEDOR: fornecedor não encontrado para o fornecedor de id {}", fornecedorId);
            throw new ResourceNotFoundException("Fornecedor não encontrado!");
        }
        log.info("FORNECEDOR: buscando informações sobre as formas de pagamento do fornecedor com id: {}", fornecedorId);
        return repository.findByFornecedorId(fornecedorId);
    }

    public List<String> findNomeFormasPagamentoAssessor(Integer assessorId){
        if (!assessorRepository.existsById(assessorId)){
            log.error("Assessor não encontrado para o id {}", assessorId);
            throw new ResourceNotFoundException("Assessor não encontrado!");
        }
        log.info("ASSESSOR: buscando informações sobre as formas de pagamento do assessor com id {}", assessorId);
        return repository.findByAssessorId(assessorId);
    }

}
