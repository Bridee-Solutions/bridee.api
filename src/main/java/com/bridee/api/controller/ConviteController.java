package com.bridee.api.controller;

import com.bridee.api.dto.request.ConviteRequestDto;
import com.bridee.api.dto.response.ConviteResponseDto;
import com.bridee.api.dto.response.ConvitesResponseDto;
import com.bridee.api.dto.response.ErrorResponseDto;
import com.bridee.api.entity.Convite;
import com.bridee.api.projection.orcamento.RelatorioProjection;
import com.bridee.api.utils.PageUtils;
import com.bridee.api.utils.UriUtils;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Tag(name = "Controller de convite")
public interface ConviteController {

    @Operation(summary = "Encontra um convite pelo id",
            description = "Busca um convite pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o convite"),
            @ApiResponse(responseCode = "404", description = "Convite não encontrado",
                    content = @Content(schema =  @Schema(implementation = ErrorResponseDto.class)))
    })
    ResponseEntity<ConvitesResponseDto> findById(@PathVariable Integer id);

    @Operation(summary = "Busca todos os convites de um casamento",
            description = "Busca todos os convites de um casamento específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna todos os convites"),
            @ApiResponse(responseCode = "404", description = "Casamento não encontrado",
                    content = @Content(schema =  @Schema(implementation = ErrorResponseDto.class)))
    })
    ResponseEntity<Page<ConvitesResponseDto>> findAllInvites(@RequestParam Map<String, Object> filter, @PathVariable Integer casamentoId);

    @Operation(summary = "Relatório de convites do casamento",
            description = "Gera um relatório sobre os status dos convites do casamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna os relátorios dos convites"),
            @ApiResponse(responseCode = "404", description = "Não encontrou os dados para gerar o relatório",
                    content = @Content(schema =  @Schema(implementation = ErrorResponseDto.class)))
    })
    ResponseEntity<RelatorioProjection> findRelatorioConviteCasamento(@PathVariable Integer casamentoId);

    @Operation(summary = "Cria um convite para um casamento",
            description = "Cria um convite para um casamento já cadastrado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cria e associa um convite a um casamento"),
            @ApiResponse(responseCode = "409", description = "Convite já cadastrado para esse casamento",
                    content = @Content(schema =  @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Titular do convite não encontrado",
                    content = @Content(schema =  @Schema(implementation = ErrorResponseDto.class)))
    })
    ResponseEntity<ConvitesResponseDto> save(@RequestBody @Valid ConviteRequestDto requestDto);

    @Operation(summary = "Atualiza um convite",
            description = "Atualiza um convite pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Convite atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Convite não encontrado",
                    content = @Content(schema =  @Schema(implementation = ErrorResponseDto.class)))
    })
    ResponseEntity<ConviteResponseDto> update(@RequestBody @Valid ConviteRequestDto requestDto,
                                                     @PathVariable Integer id);
    @Operation(summary = "Deleta um convite",
            description = "Deleta um convite pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Convite deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Convite não encontrado",
                    content = @Content(schema =  @Schema(implementation = ErrorResponseDto.class)))
    })
    ResponseEntity<Void> delete(@PathVariable Integer id);

}
