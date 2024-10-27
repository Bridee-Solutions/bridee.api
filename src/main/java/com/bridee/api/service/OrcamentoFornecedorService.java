package com.bridee.api.service;

import com.bridee.api.entity.OrcamentoFornecedor;
import com.bridee.api.projection.orcamento.OrcamentoFornecedorProjection;
import com.bridee.api.repository.OrcamentoFornecedorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrcamentoFornecedorService {

    private final OrcamentoFornecedorRepository repository;

    public void findByCasalId(Integer casalId){
        repository.findAllFornecedoresByCasalId(casalId);
        repository.findAllByCasalId(casalId);
    }

    public List<OrcamentoFornecedor> saveAll(List<OrcamentoFornecedor> orcamentoFornecedores){
        return repository.saveAll(orcamentoFornecedores);
    }

}
