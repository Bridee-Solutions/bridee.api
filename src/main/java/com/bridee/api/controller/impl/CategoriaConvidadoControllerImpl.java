package com.bridee.api.controller.impl;

import com.bridee.api.dto.response.CategoriaConvidadoResponseDto;
import com.bridee.api.entity.CategoriaConvidado;
import com.bridee.api.mapper.response.CategoriaConvidadoResponseMapper;
import com.bridee.api.service.CategoriaConvidadoService;
import feign.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/categorias-convidados")
@RequiredArgsConstructor
public class CategoriaConvidadoControllerImpl {

    private final CategoriaConvidadoService service;
    private final CategoriaConvidadoResponseMapper responseMapper;

    @GetMapping
    public ResponseEntity<List<CategoriaConvidadoResponseDto>> findAll(){
        log.info("CATEGORIA CONVIDADO: buscando todas as categorias dos convidados");
        List<CategoriaConvidado> categorias = service.findAll();
        return ResponseEntity.ok(responseMapper.toDomain(categorias));
    }

}
