package com.bridee.api.controller.impl;

import com.bridee.api.controller.SubcategoriaServicoController;
import com.bridee.api.dto.response.SubcategoriaServicoResponseDto;
import com.bridee.api.entity.SubcategoriaServico;
import com.bridee.api.mapper.response.SubcategoriaServicoResponseMapper;
import com.bridee.api.service.SubcategoriaServicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subcategorias")
@RequiredArgsConstructor
public class SubcategoriaServicoControllerImpl implements SubcategoriaServicoController {

    private final SubcategoriaServicoService subcategoriaServicoService;
    private final SubcategoriaServicoResponseMapper responseMapper;

    @GetMapping("/{categoriaId}")
    public ResponseEntity<Page<SubcategoriaServicoResponseDto>> findAllByCategoria(@PathVariable Integer categoriaId,  Pageable pageable){
        Page<SubcategoriaServico> subcategoriaServicos = subcategoriaServicoService.findAllByCategoria(categoriaId, pageable);
        Page<SubcategoriaServicoResponseDto> responseDtoPage = responseMapper.toDomain(subcategoriaServicos);
        return ResponseEntity.ok(responseDtoPage);
    }

}
