package com.bridee.api.controller;

import com.bridee.api.dto.response.ErrorResponseDto;
import com.bridee.api.dto.response.PexelsImageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Controller do pexels")
public interface PexelsController {

    @Operation(summary = "Busca imagens do pexels",
            description = "Busca imagens do pexels pelo nome")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna as imagens pesquisadas"),
            @ApiResponse(responseCode = "404", description = "Imagem não encontrada",
                    content = @Content(schema =  @Schema(implementation = ErrorResponseDto.class)))
    })
    ResponseEntity<PexelsImageResponse> findImages(String query, String page, String perPage, Integer casalId);

}
