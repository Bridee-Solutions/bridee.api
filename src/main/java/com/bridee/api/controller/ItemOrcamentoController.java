package com.bridee.api.controller;

import com.bridee.api.dto.request.ItemOrcamentoRequestDto;
import com.bridee.api.dto.response.ItemOrcamentoResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ItemOrcamentoController {

    @Operation(summary = "Vincular itens de orçamento", description = "Salva uma lista de itens de orçamento no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Itens de orçamento vinculados com sucesso",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = ItemOrcamentoResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    ResponseEntity<List<ItemOrcamentoResponseDto>> vinculateItensOrcamento(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Lista de itens de orçamento a serem vinculados",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ItemOrcamentoRequestDto.class))
            )
            @RequestBody @Valid List<ItemOrcamentoRequestDto> requestDto
    );


    @Operation(summary = "Deletar item de orçamento", description = "Deleta um item de orçamento pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item de orçamento deletado com sucesso", content = @Content),
            @ApiResponse(responseCode = "404", description = "Item de orçamento não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    ResponseEntity<Void> deleteById(
            @Parameter(description = "ID do item de orçamento a ser deletado", required = true)
            @PathVariable Integer id
    );


    @Operation(summary = "Deletar custo associado", description = "Deleta um custo associado pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Custo deletado com sucesso", content = @Content),
            @ApiResponse(responseCode = "404", description = "Custo não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    ResponseEntity<Void> deleteCustoById(
            @Parameter(description = "ID do custo associado a ser deletado", required = true)
            @PathVariable Integer id
    );
}
