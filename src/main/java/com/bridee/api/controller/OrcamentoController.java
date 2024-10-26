package com.bridee.api.controller;

import com.bridee.api.dto.request.OrcamentoCasalRequestDto;
import com.bridee.api.dto.response.CasalOrcamentoResponseDto;
import com.bridee.api.entity.Casal;
import com.bridee.api.projection.orcamento.OrcamentoProjection;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Controller de orcamento")
public interface OrcamentoController {

    @Operation(summary = "Busca o orcamento do casal",
            description = "Busca o  orcamento de um casal pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o orçamento do casal"),
            @ApiResponse(responseCode = "404", description = "Casal não encontrado")
    })
    ResponseEntity<OrcamentoProjection> findOrcamentoCasal(@PathVariable Integer id);

    @Operation(summary = "Download do CSV",
            description = "Realiza o download de um arquivo csv com o orçamento do casal")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Arquivo gerado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Não foi possível gerar o arquivo"),
            @ApiResponse(responseCode = "404", description = "Orçamento não encontrado")
    })
    ResponseEntity<byte[]> downloadOrcamentoCsv(@PathVariable Integer id);

    @Operation(summary = "Salva um item para o orçamento",
            description = "Salva um item para o orçamento de um casal")
    @ApiResponse(responseCode = "200", description = "Atualiza as informações do orçamento")
    ResponseEntity<CasalOrcamentoResponseDto> saveItemOrcamento(@RequestBody @Valid OrcamentoCasalRequestDto requestDto);

}
