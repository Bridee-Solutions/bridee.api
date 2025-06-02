package com.bridee.api.service;

import com.bridee.api.configuration.cache.CacheConstants;
import com.bridee.api.entity.SubcategoriaServico;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.repository.FornecedorRepository;
import com.bridee.api.repository.SubcategoriaServicoRepository;
import com.bridee.api.utils.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SubcategoriaServicoService {

    private final SubcategoriaServicoRepository subcategoriaServicoRepository;
    private final CategoriaServicoService categoriaServicoService;
    private final FornecedorRepository fornecedorRepository;
    private final SubcategoriaServicoServiceCacheAssistent cacheAssistent;

    public List<SubcategoriaServico> findAll(){
        return subcategoriaServicoRepository.findAllSubcategories();
    }

    @Transactional(readOnly = true)
    public Page<SubcategoriaServico> findAllByCategoria(Integer categoriaId, Pageable pageable) {
        categoriaServicoService.existsById(categoriaId);
        List<SubcategoriaServico> subcategorias = cacheAssistent.findAll()
                .stream()
                .filter(subcategoria ->
                        subcategoria.getCategoriaServico().getId().equals(categoriaId)).toList();

        return PageUtils.collectionToPage(subcategorias, pageable);
    }

    public List<SubcategoriaServico> findAllByCategoria(Integer categoriaId){
        categoriaServicoService.existsById(categoriaId);
        return cacheAssistent.findAll()
                .stream()
                .filter(subcategoria ->
                        subcategoria.getCategoriaServico().getId().equals(categoriaId)).toList();
    }

    public void existsById(Integer id){
        if(!subcategoriaServicoRepository.existsById(id)){
            throw new ResourceNotFoundException("Subcategoria não encontrada!");
        }
    }

    public SubcategoriaServico findByFornecedorId(Integer id) {
        if (!fornecedorRepository.existsById(id)){
            throw new ResourceNotFoundException("Fornecedor não encontrado!");
        }
        return subcategoriaServicoRepository.findByFornecedorId(id);
    }

    @Transactional(readOnly = true)
    public Page<SubcategoriaServico> findAllByCategoriaName(String nome, Pageable pageable) {
        List<SubcategoriaServico> subcategorias = cacheAssistent.findAll().stream()
                .filter(subcategoria -> subcategoria.getCategoriaServico().getNome().equals(nome))
                .toList();

        return PageUtils.collectionToPage(subcategorias, pageable);
    }
}
