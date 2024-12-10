package com.bridee.api.controller;

import com.bridee.api.dto.request.UpdateCasalMessageDto;
import com.bridee.api.dto.response.AssessorResponseDto;
import com.bridee.api.dto.response.ErrorResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

@Tag(name = "Controller de Casamento", description = "API para gerenciar os casamentos, seus assessores e orçamentos.")
public interface CasamentoController {

    @Operation(
        summary = "Calcula o orçamento do casamento",
        description = "Calcula e retorna o orçamento total de um casamento específico com base no ID fornecido."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Orçamento calculado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BigDecimal.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Casamento não encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
        )
    })
    ResponseEntity<BigDecimal> calculateCasamentoOrcamento(Integer casamentoId);


    @Operation(
        summary = "Vincula um assessor a um casamento",
        description = "Vincula um assessor ao casamento com base nos IDs fornecidos."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Assessor vinculado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AssessorResponseDto.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Casamento ou assessor não encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
        )
    })
    ResponseEntity<AssessorResponseDto> vinculateAssessorToWedding(Integer casamentoId, Integer assessorId);


    @Operation(
        summary = "Aceita a proposta de um casamento",
        description = "Aceita a proposta de um casamento vinculada a um assessor e um casamento com base nos IDs fornecidos."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Proposta aceita com sucesso"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Casamento ou assessor não encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
        )
    })
    ResponseEntity<Void> acceptWedding(Integer casamentoId, Integer assessorId);


    @Operation(
        summary = "Recusa a proposta de um casamento",
        description = "Recusa a proposta de um casamento vinculada a um assessor e um casamento com base nos IDs fornecidos."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Proposta recusada com sucesso"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Casamento ou assessor não encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
        )
    })
    ResponseEntity<Void> denyWedding(Integer casamentoId, Integer assessorId);


    @Operation(
        summary = "Remove a proposta de um casamento",
        description = "Remove a proposta de um casamento vinculada a um assessor e um casamento com base nos IDs fornecidos."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Proposta removida com sucesso"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Casamento ou assessor não encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
        )
    })
    ResponseEntity<Void> removeWeddingAdvise(Integer casamentoId, Integer assessorId);


    @Operation(
        summary = "Atualiza a mensagem de um casamento",
        description = "Atualiza a mensagem que o casal pode visualizar em seu painel de casamento."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Mensagem atualizada com sucesso"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Casamento não encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Requisição inválida",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
        )
    })
    ResponseEntity<Void> updateCasamentoMessage(Integer casamentoId, UpdateCasalMessageDto request);
}
