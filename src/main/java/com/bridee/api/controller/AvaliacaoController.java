package com.bridee.api.controller;

import com.bridee.api.dto.request.AvaliacaoRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Controller de avaliação")
public interface AvaliacaoController {

    @Operation(summary = "Cadastra a avaliação do casal",
            description = "Cadastra a avalição do casal para um assessor ou fornecedor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400",
                    description = "Tentativa de avaliar um assessor e fornecedor ao mesmo tempo"),
            @ApiResponse(responseCode = "200",
                    description = "Avaliação criada com sucesso")
    })
    ResponseEntity<Void> saveAvaliacao(@RequestBody @Valid AvaliacaoRequestDto requestDto);
}
