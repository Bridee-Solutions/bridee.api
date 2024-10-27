package com.bridee.api.controller;

import com.bridee.api.dto.response.CategoriaServicoResponseDto;
import com.bridee.api.entity.CategoriaServico;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@Tag(name = "Controller CategoriaServico")
public interface CategoriaServicoController {

    @Operation(summary = "Lista todas as categorias de serviço",
            description = "Lista todas as categorias de serviço de forma paginada")
    @ApiResponse(responseCode = "200", description = "Retorna todas as categorias de serviço")
    ResponseEntity<Page<CategoriaServicoResponseDto>> findAll(Pageable pageable);

}
