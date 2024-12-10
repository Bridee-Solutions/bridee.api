package com.bridee.api.controller;

import com.bridee.api.dto.response.DashboardResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Dashboard", description = "Endpoints para visualização de informações de dashboard")
public interface DashboardController {

    @Operation(
        summary = "Visualizar o dashboard do casamento",
        description = "Retorna as informações de dashboard associadas a um casamento específico."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Dashboard retornado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Casamento não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor ao tentar recuperar o dashboard")
    })
    ResponseEntity<DashboardResponseDto> dashboard(@PathVariable Integer casamentoId);
}
