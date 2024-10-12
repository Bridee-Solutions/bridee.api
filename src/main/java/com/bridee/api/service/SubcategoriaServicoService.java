package com.bridee.api.service;

import com.bridee.api.entity.SubcategoriaServico;
import com.bridee.api.repository.SubcategoriaServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubcategoriaServicoService {

    private final SubcategoriaServicoRepository subcategoriaServicoRepository;
    private final CategoriaServicoService categoriaServicoService;

    public Page<SubcategoriaServico> findAllByCategoria(Integer categoriaId, Pageable pageable) {
        categoriaServicoService.existsById(categoriaId);
        return subcategoriaServicoRepository.findAllByCategoriaNome(categoriaId, pageable);
    }
}
