package com.bridee.api.controller;

import com.bridee.api.dto.request.FornecedorRequestDto;
import com.bridee.api.dto.response.ErrorResponseDto;
import com.bridee.api.dto.response.FornecedorResponseDto;
import com.bridee.api.repository.projection.associado.AssociadoGeralResponseDto;
import com.bridee.api.repository.projection.associado.AssociadoResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Controller de fornecedor")
public interface FornecedorController {

    @Operation(summary = "Busca todos os fornecedores paginados",
            description = "Busca todos os fornecedores paginados")
    @ApiResponse(responseCode = "200", description = "Retorna todos os fornecedores")
    ResponseEntity<Page<FornecedorResponseDto>> findAll(Pageable pageable);

    @Operation(summary = "Busca um fornecedor",
            description = "Busca um fornecedor específico pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna um fornecedor específico"),
            @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado",
                    content = @Content(schema =  @Schema(implementation = ErrorResponseDto.class)))
    })
    ResponseEntity<FornecedorResponseDto> findById(@PathVariable Integer id);

    @Operation(summary = "Busca os detalhes do fornecedor",
            description = "Busca os detalhes de um fornecedor pela categoria")
    @ApiResponse(responseCode = "200", description = "Retorna os detalhes do fornecedor")
    ResponseEntity<Page<FornecedorResponseDto>> findFornecedorDetailsByCategoria(Integer categoriaId,
                                                                                String nome,
                                                                                Pageable pageable);

    @Operation(summary = "Busca os detalhes do fornecedor",
            description = "Busca os detalhes de um fornecedor pela subcategoria")
    @ApiResponse(responseCode = "200", description = "Retorna os detalhes do fornecedor")
    ResponseEntity<Page<AssociadoResponseDto>> findFornecedorDetails(Integer subcategoriaId,
                                                                     String nome,
                                                                     Pageable pageable);

    @Operation(summary = "Busca as informações do fornecedor",
            description = "Busca as informações de um fornecedor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna as informações do fornecedor"),
            @ApiResponse(responseCode = "404", description = "Fornecedor ou informações não encontradas",
                    content = @Content(schema =  @Schema(implementation = ErrorResponseDto.class)))
    })
    ResponseEntity<AssociadoGeralResponseDto> findFornecedorInformation(@PathVariable Integer id);

    @Operation(summary = "Cadastra um fornecedor",
            description = "Cadastra um fornecedor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cria um fornecedor"),
            @ApiResponse(responseCode = "409", description = "Fornecedor já existe",
                    content = @Content(schema =  @Schema(implementation = ErrorResponseDto.class)))
    })
    ResponseEntity<FornecedorResponseDto> save(@RequestBody @Valid FornecedorRequestDto fornecedorRequestDto);

    @Operation(summary = "Atualiza um fornecedor",
            description = "Atualiza um fornecedor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cria um fornecedor"),
            @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado",
                    content = @Content(schema =  @Schema(implementation = ErrorResponseDto.class)))
    })
    ResponseEntity<FornecedorResponseDto> update(@RequestBody @Valid FornecedorRequestDto fornecedorRequestDto, @PathVariable Integer id);

    @Operation(summary = "Deleta um fornecedor",
            description = "Deleta um fornecedor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleta um fornecedor"),
            @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado",
                    content = @Content(schema =  @Schema(implementation = ErrorResponseDto.class)))
    })
    ResponseEntity<Void> delete(@PathVariable Integer id);
}
