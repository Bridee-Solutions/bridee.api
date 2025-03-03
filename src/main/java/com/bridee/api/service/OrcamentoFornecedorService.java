package com.bridee.api.service;

import com.bridee.api.entity.OrcamentoFornecedor;
import com.bridee.api.entity.SubcategoriaServico;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.exception.UnprocessableEntityException;
import com.bridee.api.mapper.response.OrcamentoFornecedorResponseMapper;
import com.bridee.api.repository.projection.orcamento.orcamentofornecedor.OrcamentoFornecedorProjection;
import com.bridee.api.repository.OrcamentoFornecedorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OrcamentoFornecedorService {

    private final OrcamentoFornecedorRepository repository;
    private final OrcamentoFornecedorResponseMapper responseMapper;
    private final SubcategoriaServicoService subcategoriaServicoService;
    private final FornecedorService fornecedorService;

    public void findByCasalId(Integer casalId){
        repository.findAllFornecedoresByCasalId(casalId);
        repository.findAllByCasalId(casalId);
    }

    public List<OrcamentoFornecedorProjection> findAllOrcamentoFornecedorByCasalId(Integer casalId){
        return repository.findAllByCasalId(casalId);
    }

    public List<OrcamentoFornecedor> saveAll(List<OrcamentoFornecedor> orcamentoFornecedores){
        Integer casalId = orcamentoFornecedores.get(0).getCasal().getId();
        log.info("ORCAMENTO FORNECEDOR: salvando o orcamento dos fornecedores, para o casal de id {}", casalId);
        if (orcamentoFornecedores.isEmpty()) {
            log.error("ORCAMENTO FORNECEDOR: nenhum orcamento fornecedor informado");
            throw new UnprocessableEntityException("Nenhum orcamentoFornecedor informado!");
        }
        removeInactivesOrcamentoFornecedor(orcamentoFornecedores);
        return repository.saveAll(orcamentoFornecedores);
    }

    private void removeInactivesOrcamentoFornecedor(List<OrcamentoFornecedor> orcamentoFornecedores){
        Integer casalId = orcamentoFornecedores.get(0).getCasal().getId();
        List<OrcamentoFornecedor> allOrcamentoFornecedores = responseMapper
                .fromProjection(repository.findAllBaseProjectionByCasalId(casalId));
        List<Integer> orcamentoFornecedoresIdsToBeRemoved = orcamentoFornecedoresToBeRemoved(allOrcamentoFornecedores, orcamentoFornecedores);

        log.info("ORCAMENTO FORNECEDOR: removendo {} orcamento fornecedores inativos", orcamentoFornecedoresIdsToBeRemoved.size());
        repository.deleteAllById(orcamentoFornecedoresIdsToBeRemoved);
    }

    private List<Integer> orcamentoFornecedoresToBeRemoved(List<OrcamentoFornecedor> allOrcamentoFornecedores,
                                                                       List<OrcamentoFornecedor> orcamentoFornecedores) {
        List<Integer> allOrcamentoFornecedoresId = allOrcamentoFornecedores.stream().map(OrcamentoFornecedor::getId).toList();
        List<Integer> orcamentoFornecedoresId = allOrcamentoFornecedores.stream().map(OrcamentoFornecedor::getId).toList();
        return allOrcamentoFornecedoresId.stream().filter((id) -> !orcamentoFornecedoresId.contains(id)).toList();
    }

    public OrcamentoFornecedor saveOrcamentoFornecedorCasal(OrcamentoFornecedor orcamentoFornecedor, Integer categoriaId) {
        Integer fornecedorId = orcamentoFornecedor.getFornecedor().getId();
        SubcategoriaServico subcategoria = findSubcategoriaFornecedor(fornecedorId, categoriaId);
        removePreviousOrcamentoFornecedorIfExists(orcamentoFornecedor, subcategoria);

        orcamentoFornecedor = repository.save(orcamentoFornecedor);
        orcamentoFornecedor.setFornecedor(orcamentoFornecedor.getFornecedor());
        return orcamentoFornecedor;
    }

    private void removePreviousOrcamentoFornecedorIfExists(OrcamentoFornecedor orcamentoFornecedor, SubcategoriaServico subcategoria) {
        Integer casalId = orcamentoFornecedor.getCasal().getId();
        List<OrcamentoFornecedor> orcamentosFornecedoresCadastrados = repository.findAllByCasalIdAndSubcategoriaId(casalId, subcategoria.getId());

        if (!orcamentosFornecedoresCadastrados.isEmpty()){
            deleteAllOrcamentoFornecedores(orcamentosFornecedoresCadastrados);
        }
    }

    private SubcategoriaServico findSubcategoriaFornecedor(Integer fornecedorId, Integer categoriaId){
        List<SubcategoriaServico> subcategoriasFromCategoria = subcategoriaServicoService.findAllByCategoria(categoriaId);
        SubcategoriaServico subcategoriaServicoFornecedor = subcategoriaServicoService.findByFornecedorId(fornecedorId);

        Optional<SubcategoriaServico> subcategoriaOpt = subcategoriasFromCategoria.stream()
                .filter(subcategoria -> subcategoria.getId().equals(subcategoriaServicoFornecedor.getId()))
                .findFirst();

        if (subcategoriaOpt.isEmpty()){
            log.error("SUBCATEGORIA FORNECEDOR: subcategoria não encotrada com a categoria de id {}", categoriaId);
            throw new ResourceNotFoundException("Subcategoria não encontrada para a categoria informada");
        }

        return subcategoriaOpt.get();
    }

    private void deleteAllOrcamentoFornecedores(List<OrcamentoFornecedor> orcamentoFornecedores){
        List<Integer> orcamentosFornecedoresCadastradosIds = orcamentoFornecedores.stream()
                .map(OrcamentoFornecedor::getId).toList();
        repository.deleteAllById(orcamentosFornecedoresCadastradosIds);
    }

    public void updateOrcamentoFornecedorPreco(Integer id,BigDecimal preco) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Orcamento não encontrado para o fornecedor");
        }
        repository.updateOrcamentoFornecedorPreco(id, preco);
    }
}
