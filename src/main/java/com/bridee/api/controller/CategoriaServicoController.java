package com.bridee.api.controller;

import com.bridee.api.dto.response.CategoriaServicoResponseDto;
import com.bridee.api.entity.CategoriaServico;
import com.bridee.api.mapper.response.CategoriaServicoResponseMapper;
import com.bridee.api.service.CategoriaServicoService;
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
public class CategoriaServicoController {

    private final CategoriaServicoService service;
    private final CategoriaServicoResponseMapper responseMapper;

    @GetMapping
    public ResponseEntity<Page<CategoriaServicoResponseDto>> findAll(Pageable pageable){
        Page<CategoriaServico> categoriaServicos = service.findAll(pageable);
        Page<CategoriaServicoResponseDto> responseDtoPage = responseMapper.toDomain(categoriaServicos);
        return ResponseEntity.ok(responseDtoPage);
    }

}
