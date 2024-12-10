package com.bridee.api.controller;

import com.bridee.api.dto.response.CategoriaConvidadoResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Tag(name = "Categoria de Convidados", description = "Endpoints para gerenciar categorias de convidados")
public interface CategoriaConvidadoController {

    @Operation(
        summary = "Listar todas as categorias de convidados",
        description = "Retorna uma lista de todas as categorias de convidados disponíveis."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de categorias de convidados retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor ao tentar recuperar as categorias")
    })
    @GetMapping
    ResponseEntity<List<CategoriaConvidadoResponseDto>> findAll();
}
