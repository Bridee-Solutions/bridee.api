package com.bridee.api.controller;

import com.bridee.api.dto.request.TarefaRequestDto;
import com.bridee.api.dto.response.TarefaResponseDto;
import com.bridee.api.dto.response.TarefasResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "Tarefa Controller", description = "Gerencia as operações CRUD para as tarefas do casamento.")
public interface TarefaController {

    @Operation(summary = "Listar todas as tarefas do casamento",
               description = "Recupera todas as tarefas associadas a um casamento.",
               responses = {
                   @ApiResponse(responseCode = "200", description = "Lista de tarefas do casamento",
                                content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
               })
    @GetMapping("/casamento/{casamentoId}")
    ResponseEntity<List<TarefasResponseDto>> findAll(
        @Parameter(description = "ID do casamento para o qual as tarefas serão recuperadas") @PathVariable Integer casamentoId,
        @RequestParam Map<String, Object> params);

    @Operation(summary = "Criar uma nova tarefa",
               description = "Cria uma nova tarefa e a associa ao casamento especificado.",
               responses = {
                   @ApiResponse(responseCode = "201", description = "Tarefa criada com sucesso",
                                content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                   @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
               })
    @PostMapping("/casamento/{casamentoId}")
    ResponseEntity<TarefaResponseDto> save(
        @Parameter(description = "ID do casamento ao qual a tarefa será associada") @PathVariable Integer casamentoId,
        @RequestBody @Valid TarefaRequestDto requestDto);

    @Operation(summary = "Atualizar uma tarefa",
               description = "Atualiza os dados de uma tarefa existente associada ao casamento.",
               responses = {
                   @ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso",
                                content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                   @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
               })
    @PutMapping("/casamento/{casamentoId}/tarefa/{tarefaId}")
    ResponseEntity<TarefaResponseDto> update(
        @Parameter(description = "ID do casamento") @PathVariable Integer casamentoId,
        @Parameter(description = "ID da tarefa a ser atualizada") @PathVariable Integer tarefaId,
        @RequestBody @Valid TarefaRequestDto requestDto);

    @Operation(summary = "Excluir uma tarefa",
               description = "Exclui a tarefa com o ID especificado.",
               responses = {
                   @ApiResponse(responseCode = "204", description = "Tarefa excluída com sucesso"),
                   @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
               })
    @DeleteMapping("/{id}")
    ResponseEntity<TarefaResponseDto> delete(
        @Parameter(description = "ID da tarefa a ser excluída") @PathVariable Integer id);
}
