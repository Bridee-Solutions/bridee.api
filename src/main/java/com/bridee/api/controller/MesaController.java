package com.bridee.api.controller;

import com.bridee.api.dto.request.MesaRequestDto;
import com.bridee.api.dto.response.ConvidadoResponseDto;
import com.bridee.api.dto.response.MesaResponseDto;
import com.bridee.api.entity.Convidado;
import com.bridee.api.entity.Mesa;
import com.bridee.api.utils.PageUtils;
import com.bridee.api.utils.UriUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Controller de mesa")
public interface MesaController {

    @Operation(summary = "Busca todas as mesas de um casamento",
            description = "Busca todas as mesas paginadas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna todas as mesas"),
            @ApiResponse(responseCode = "404", description = "Casamento não encontrado")
    })
    ResponseEntity<Page<MesaResponseDto>> findAll(@PathVariable Integer casamentoId, Pageable pageable);

    @Operation(summary = "Busca todos os convidados sem mesa de um casamento",
            description = "Busca todos os convidados sem mesa de um casamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna todos os convidados sem mesa")
    })
    ResponseEntity<Page<ConvidadoResponseDto>> findConvidadosWithoutMesa(@RequestParam Map<String, Object> params,
                                                                                @PathVariable Integer casamentoId);
    @Operation(summary = "Cria uma mesa vinculada a um casamento",
            description = "Cria uma mesa vinculada a um casamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Mesa criada com sucesso"),
            @ApiResponse(responseCode = "409", description = "Mesa já cadastrada para o casamento")
    })
    ResponseEntity<MesaResponseDto> save(@RequestBody MesaRequestDto requestDto,
                                                @PathVariable Integer casamentoId);

    @Operation(summary = "Atualiza uma mesa",
            description = "Atualiza uma mesa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Mesa atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Mesa não encontrada")
    })
    ResponseEntity<MesaResponseDto> update(@RequestBody MesaRequestDto requestDto,
                                                  @PathVariable Integer id);
    @Operation(summary = "Deleta uma mesa",
            description = "Deleta uma mesa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Mesa deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Mesa não encontrada")
    })
    ResponseEntity<Void> deleteById(@PathVariable Integer id);
}
