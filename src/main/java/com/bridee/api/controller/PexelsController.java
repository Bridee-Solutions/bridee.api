package com.bridee.api.controller;

import com.bridee.api.client.dto.response.PexelsImageResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Controller do pexels")
public interface PexelsController {

    @Operation(summary = "Busca imagens do pexels",
            description = "Busca imagens do pexels pelo nome")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna as imagens pesquisadas"),
            @ApiResponse(responseCode = "404", description = "Imagem não encontrada")
    })
    ResponseEntity<PexelsImageResponseDto> findImages(@RequestParam String query);

}
