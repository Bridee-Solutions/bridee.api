package com.bridee.api.service;

import com.bridee.api.entity.OrcamentoFornecedor;
import com.bridee.api.exception.UnprocessableEntityException;
import com.bridee.api.mapper.response.OrcamentoFornecedorResponseMapper;
import com.bridee.api.projection.orcamento.orcamentofornecedor.OrcamentoFornecedorProjection;
import com.bridee.api.repository.OrcamentoFornecedorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class OrcamentoFornecedorService {

    private final OrcamentoFornecedorRepository repository;
    private final OrcamentoFornecedorResponseMapper responseMapper;

    public void findByCasalId(Integer casalId){
        repository.findAllFornecedoresByCasalId(casalId);
        repository.findAllByCasalId(casalId);
    }

    public List<OrcamentoFornecedorProjection> findAllOrcamentoFornecedorByCasalId(Integer casalId){
        return repository.findAllByCasalId(casalId);
    }

    public List<OrcamentoFornecedor> saveAll(List<OrcamentoFornecedor> orcamentoFornecedores){
        if (orcamentoFornecedores.isEmpty()) {
            throw new UnprocessableEntityException("Nenhum orcamentoFornecedor informado!");
        }

        removeInactivesOrcamentoFornecedor(orcamentoFornecedores);
        return repository.saveAll(orcamentoFornecedores);
    }

    private void removeInactivesOrcamentoFornecedor(List<OrcamentoFornecedor> orcamentoFornecedores){

        Integer casalId = orcamentoFornecedores.get(0).getCasal().getId();
        repository.findFornecedoresBaseProjectionByCasalId(casalId);

        List<OrcamentoFornecedor> allOrcamentoFornecedores = responseMapper
                .fromProjection(repository.findAllBaseProjectionByCasalId(casalId));

        List<Integer> orcamentoFornecedoresIdsToBeRemoved = orcamentoFornecedoresToBeRemoved(allOrcamentoFornecedores, orcamentoFornecedores);
        repository.deleteAllById(orcamentoFornecedoresIdsToBeRemoved);
    }

    private List<Integer> orcamentoFornecedoresToBeRemoved(List<OrcamentoFornecedor> allOrcamentoFornecedores,
                                                                       List<OrcamentoFornecedor> orcamentoFornecedores) {
        List<Integer> allOrcamentoFornecedoresId = allOrcamentoFornecedores.stream().map(OrcamentoFornecedor::getId).toList();
        List<Integer> orcamentoFornecedoresId = allOrcamentoFornecedores.stream().map(OrcamentoFornecedor::getId).toList();
        return allOrcamentoFornecedoresId.stream().filter((id) -> !orcamentoFornecedoresId.contains(id)).toList();
    }

}
