package com.bridee.api.service;

import com.bridee.api.entity.FormaPagamento;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.repository.FormaPagamentoRepository;
import com.bridee.api.repository.FornecedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FormaPagamentoService {

    private final FormaPagamentoRepository repository;
    private final FornecedorRepository fornecedorRepository;

    public List<String> findNomeFormasPagamentoFornecedor(Integer fornecedorId){
        return findFormasPagamentoFornecedor(fornecedorId).stream()
                .map(FormaPagamento::getNome).toList();
    }

    private List<FormaPagamento> findFormasPagamentoFornecedor(Integer fornecedorId){
        if (!fornecedorRepository.existsById(fornecedorId)){
            throw new ResourceNotFoundException("Fornecedor não encontrado!");
        }
        return repository.findByFornecedorId(fornecedorId);
    }

}
