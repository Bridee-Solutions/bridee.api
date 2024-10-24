package com.bridee.api.controller;

import com.bridee.api.dto.request.ConvidadoRequestDto;
import com.bridee.api.dto.request.MesaConvidadoRequestDto;
import com.bridee.api.dto.response.ConvidadoResponseDto;
import com.bridee.api.entity.Convidado;
import com.bridee.api.utils.UriUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Controller de convidado")
public interface ConvidadoController {

    @Operation(summary = "Busca um convidado",
            description = "Busca um convidado pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o convidado"),
            @ApiResponse(responseCode = "404", description = "Convidado não encontrado")
    })
    ResponseEntity<ConvidadoResponseDto> findById(@PathVariable Integer id);

    @Operation(summary = "Vincula os usuários as mesas",
            description = "Vincula os usuários as suas respectivas mesas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vincula mesas e convidados"),
            @ApiResponse(responseCode = "404", description = "Convidados não existem")
    })
    ResponseEntity<Void> vinculateToMesa(@RequestBody @Valid List<MesaConvidadoRequestDto> requestDtoList);

    @Operation(summary = "Cria um convidado para um convite",
            description = "Cria um convidado, vinculando a um convite já existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Convidado criado com sucesso"),
            @ApiResponse(responseCode = "409", description = "Convidado já cadastrado para o convite")
    })
    ResponseEntity<ConvidadoResponseDto> create(@RequestBody @Valid ConvidadoRequestDto requestDto,
                                                       @PathVariable Integer conviteId);
    @Operation(summary = "Atualiza um convidado",
            description = "Atualiza um convidado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Convidado atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Convidado não encontrado")
    })
    ResponseEntity<ConvidadoResponseDto> update(@PathVariable Integer id,
                                                       @RequestBody @Valid ConvidadoRequestDto requestDto);
    @Operation(summary = "Deleta um convidado",
            description = "Deleta um convidado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Convidado deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Convidado não encontrado")
    })
    ResponseEntity<Void> deleteById(@PathVariable Integer id);

}
