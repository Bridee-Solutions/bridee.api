package com.bridee.api.service;

import com.bridee.api.entity.SubcategoriaServico;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.projection.orcamento.SubcategoriaProjection;
import com.bridee.api.repository.SubcategoriaServicoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SubcategoriaServicoService {

    private final SubcategoriaServicoRepository subcategoriaServicoRepository;
    private final CategoriaServicoService categoriaServicoService;

    public List<SubcategoriaProjection> findAll(){
        return subcategoriaServicoRepository.findAllProjections();
    }

    public Page<SubcategoriaServico> findAllByCategoria(Integer categoriaId, Pageable pageable) {
        categoriaServicoService.existsById(categoriaId);
        return subcategoriaServicoRepository.findAllByCategoriaNome(categoriaId, pageable);
    }

    public void existsById(Integer id){
        if(!subcategoriaServicoRepository.existsById(id)){
            throw new ResourceNotFoundException("Subcategoria não encontrada!");
        }
    }
}
