package com.bridee.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.bridee.api.dto.request.AtividadeRequestDto;
import com.bridee.api.dto.response.AtividadeResponseDto;
import com.bridee.api.dto.response.ErrorResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Controller de atividade", description = "API para gerenciar atividades relacionadas ao casamento.")
public interface AtividadeController {

    @Operation(
        summary = "Encontra todas atividades pelo casamentoID",
        description = "Retorna uma lista de todas as atividades associadas ao casamento fornecido."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Retorna uma lista de Atividades relacionadas ao casamento",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AtividadeResponseDto.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Casamento não encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
        )
    })
    ResponseEntity<List<AtividadeResponseDto>> findAllAtividadesByCasamentoId(@PathVariable Integer casamentoId);
    

    @Operation(
        summary = "Cria uma atividade",
        description = "Cria uma nova atividade e a adiciona ao cronograma do casal a partir do cronogramaId fornecido."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Atividade criada com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AtividadeResponseDto.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Role de assessor não encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Requisição inválida",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
        )
    })
    ResponseEntity<AtividadeResponseDto> saveAtividade(@RequestBody AtividadeRequestDto requestDto, @PathVariable Integer cronogramaId);
    

    @Operation(
        summary = "Atualiza uma atividade",
        description = "Atualiza as informações de uma atividade existente com base no ID fornecido."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Atividade atualizada com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AtividadeResponseDto.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Atividade não encontrada",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Requisição inválida",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
        )
    })
    ResponseEntity<AtividadeResponseDto> updateAtividade(@RequestBody AtividadeRequestDto requestDto, @PathVariable Integer id);
    

    @Operation(
        summary = "Exclui uma atividade",
        description = "Remove uma atividade do cronograma com base no ID fornecido."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Atividade excluída com sucesso"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Atividade não encontrada",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
        )
    })
    ResponseEntity<Void> deleteAtividade(@PathVariable Integer id);
}