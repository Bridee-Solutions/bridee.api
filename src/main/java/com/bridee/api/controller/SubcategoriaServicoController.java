package com.bridee.api.controller;

import com.bridee.api.dto.response.SubcategoriaServicoResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Subcategoria Servico Controller")
public interface SubcategoriaServicoController {

    @Operation(summary = "Busca todas as subcategorias",
            description = "Busca todas as subcategorias cadastradas de uma categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna todas as subcategorias"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    ResponseEntity<Page<SubcategoriaServicoResponseDto>> findAllByCategoria(@PathVariable Integer categoriaId, Pageable pageable);

}
