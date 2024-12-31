package com.bridee.api.controller;

import com.bridee.api.dto.request.AssociadoPrecoRequestDto;
import com.bridee.api.dto.request.OrcamentoFornecedorRequestDto;
import com.bridee.api.dto.response.OrcamentoFornecedorResponseDto;
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

/**
 * Interface de documentação para o OrcamentoFornecedorController.
 */
public interface OrcamentoFornecedorController {

    @Operation(summary = "Associar lista de fornecedores ao casal", 
               description = "Salva uma lista de fornecedores associados ao casal.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fornecedores associados com sucesso",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = OrcamentoFornecedorResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    ResponseEntity<List<OrcamentoFornecedorResponseDto>> associateFornecedoresCasal(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Lista de fornecedores a serem associados ao casal",
                    required = true,
                    content = @Content(schema = @Schema(implementation = OrcamentoFornecedorRequestDto.class))
            )
            @RequestBody @Valid List<OrcamentoFornecedorRequestDto> requestDto
    );


    @Operation(summary = "Salvar orçamento de fornecedor do casal", 
               description = "Salva o orçamento de um fornecedor para um casal em uma categoria específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Orçamento salvo com sucesso",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = OrcamentoFornecedorResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    ResponseEntity<OrcamentoFornecedorResponseDto> saveOrcamentoFornecedorCasal(
            @Parameter(description = "ID da categoria", required = true) 
            @PathVariable Integer categoriaId,
            
            @Parameter(description = "ID do casamento", required = true) 
            @PathVariable Integer casamentoId,
            
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do orçamento do fornecedor a serem salvos",
                    required = true,
                    content = @Content(schema = @Schema(implementation = OrcamentoFornecedorRequestDto.class))
            )
            @RequestBody @Valid OrcamentoFornecedorRequestDto requestDto
    );


    @Operation(summary = "Atualizar o preço de um orçamento", 
               description = "Atualiza o preço de um orçamento de fornecedor existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Preço atualizado com sucesso", content = @Content),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content),
            @ApiResponse(responseCode = "404", description = "Orçamento não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    ResponseEntity<Void> updateOrcamentoFornecedor(
            @Parameter(description = "ID do orçamento de fornecedor a ser atualizado", required = true) 
            @PathVariable Integer id,
            
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados para atualização do preço",
                    required = true,
                    content = @Content(schema = @Schema(implementation = AssociadoPrecoRequestDto.class))
            )
            @RequestBody @Valid AssociadoPrecoRequestDto requestDto
    );
}
