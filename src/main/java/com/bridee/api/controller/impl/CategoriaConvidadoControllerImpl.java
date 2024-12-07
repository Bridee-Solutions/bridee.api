package com.bridee.api.controller.impl;

import com.bridee.api.dto.response.CategoriaConvidadoResponseDto;
import com.bridee.api.entity.CategoriaConvidado;
import com.bridee.api.mapper.response.CategoriaConvidadoResponseMapper;
import com.bridee.api.service.CategoriaConvidadoService;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categorias-convidados")
@RequiredArgsConstructor
public class CategoriaConvidadoControllerImpl {

    private final CategoriaConvidadoService service;
    private final CategoriaConvidadoResponseMapper responseMapper;

    @GetMapping
    public ResponseEntity<List<CategoriaConvidadoResponseDto>> findAll(){
        List<CategoriaConvidado> categorias = service.findAll();
        return ResponseEntity.ok(responseMapper.toDomain(categorias));
    }

}
