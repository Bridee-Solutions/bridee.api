package com.bridee.api.service;

import com.bridee.api.entity.TipoCasamento;
import com.bridee.api.repository.TipoCasamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoCasamentoService {

    private final TipoCasamentoRepository repository;

    public List<String> findNomeTiposCasamentoFornecedor(Integer id) {
        return findAllByInformacaoFornecedorId(id).stream().map(TipoCasamento::getNome).toList();
    }

    public List<String> findNomeTiposCasamentoAssessor(Integer id){
        return findAllByInformacaoAssessorId(id).stream().map(TipoCasamento::getNome).toList();
    }

    private List<TipoCasamento> findAllByInformacaoFornecedorId(Integer id){
        return repository.findByInformacaoFornecedorId(id);
    }

    private List<TipoCasamento> findAllByInformacaoAssessorId(Integer id){
        return repository.findByInformacaoAssessorId(id);
    }
}
