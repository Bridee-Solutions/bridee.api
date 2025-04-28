package com.bridee.api.controller;

import com.bridee.api.dto.request.CasalRequestDto;
import com.bridee.api.dto.request.OrcamentoTotalRequestDto;
import com.bridee.api.dto.request.externo.CasalExternoRequestDto;
import com.bridee.api.dto.response.CasalResponseDto;
import com.bridee.api.dto.response.ErrorResponseDto;
import com.bridee.api.dto.response.externo.CasalExternoResponseDto;
import com.bridee.api.entity.Casal;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.json.JsonMergePatch;
import java.math.BigDecimal;

@Tag(name = "Controller de casal")
public interface CasalController {

    @Operation(summary = "Busca todos os casais",
                description = "Busca todos os casais cadastrados com paginação")
    @ApiResponse(responseCode = "200", description = "Retorna uma lista de casais")
    ResponseEntity<Page<CasalResponseDto>> findAll(Pageable pageable);

    @Operation(summary = "Busca um casal",
            description = "Busca um casal específico pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca um casal pelo id"),
            @ApiResponse(responseCode = "404", description = "Casal não encontrado",
                    content = @Content(schema =  @Schema(implementation = ErrorResponseDto.class)))
    })
    ResponseEntity<CasalResponseDto> findById(@PathVariable Integer id);

    @Operation(summary = "Salva um casal",
            description = "Salva um casal na aplicação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cria um casal"),
            @ApiResponse(responseCode = "409", description = "Email do casal já cadastrado",
                    content = @Content(schema =  @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Role de casal não encontrado",
                    content = @Content(schema =  @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "422", description = "Tentativa de cadastrar um usuário externo",
                    content = @Content(schema =  @Schema(implementation = ErrorResponseDto.class)))
    }
    )
    ResponseEntity<CasalResponseDto> save(@RequestBody @Valid CasalRequestDto requestDto);

    @Operation(summary = "Salva um casal",
            description = "Salva um casal na aplicação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cria um casal"),
            @ApiResponse(responseCode = "409", description = "Email do casal já cadastrado",
                    content = @Content(schema =  @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Role de casal não encontrado",
                    content = @Content(schema =  @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "422", description = "Tentativa de cadastrar um usuário não externo",
                    content = @Content(schema =  @Schema(implementation = ErrorResponseDto.class)))
    }
    )
    ResponseEntity<CasalExternoResponseDto> saveExterno(@RequestBody @Valid CasalExternoRequestDto requestDto);

    @Operation(summary = "Atualiza um casal",
            description = "Atualização um casal na aplicação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Atualiza um casal"),
            @ApiResponse(responseCode = "404", description = "Casal não encontrado",
                    content = @Content(schema =  @Schema(implementation = ErrorResponseDto.class))),
    }
    )
    ResponseEntity<CasalResponseDto> update(@RequestBody JsonMergePatch jsonMergePatch, @PathVariable Integer id);

    @Operation(summary = "Atualiza o orcamento de um casal",
            description = "Atualiza o orcamento de um casal pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Atualiza um casal"),
            @ApiResponse(responseCode = "404", description = "Casal não encontrado",
                    content = @Content(schema =  @Schema(implementation = ErrorResponseDto.class))),
    }
    )
    ResponseEntity<CasalResponseDto> updateOrcamentoTotal(Integer id, @RequestBody OrcamentoTotalRequestDto orcamentoTotal);
}
