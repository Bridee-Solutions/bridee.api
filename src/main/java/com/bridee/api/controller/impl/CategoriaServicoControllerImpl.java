package com.bridee.api.controller.impl;

import com.bridee.api.controller.CategoriaServicoController;
import com.bridee.api.dto.response.CategoriaServicoResponseDto;
import com.bridee.api.dto.response.SubcategoriaServicoResponseDto;
import com.bridee.api.entity.CategoriaServico;
import com.bridee.api.mapper.response.CategoriaServicoResponseMapper;
import com.bridee.api.mapper.response.SubcategoriaServicoResponseMapper;
import com.bridee.api.service.CategoriaServicoService;
import com.bridee.api.service.SubcategoriaServicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categorias-servicos")
@RequiredArgsConstructor
public class CategoriaServicoControllerImpl implements CategoriaServicoController {

    private final CategoriaServicoService service;
    private final SubcategoriaServicoService subcategoriaServicoService;
    private final CategoriaServicoResponseMapper responseMapper;
    private final SubcategoriaServicoResponseMapper subcategoriaServicoResponseMapper;

    @GetMapping
    public ResponseEntity<Page<CategoriaServicoResponseDto>> findAll(Pageable pageable){
        Page<CategoriaServico> categoriaServicos = service.findAll(pageable);
        Page<CategoriaServicoResponseDto> responseDtoPage = responseMapper.toDomain(categoriaServicos);
        responseDtoPage.getContent().forEach(content -> {
            List<SubcategoriaServicoResponseDto> subcategorias = subcategoriaServicoService.findAllByCategoria(content.getId())
                    .stream().map(subcategoriaServicoResponseMapper::toDomain).toList();
            content.setSubcategorias(subcategorias);
        });
        return ResponseEntity.ok(responseDtoPage);
    }

}
