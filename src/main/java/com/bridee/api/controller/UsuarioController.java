package com.bridee.api.controller;

import com.bridee.api.dto.response.UsuarioResponseDto;
import com.bridee.api.entity.Usuario;
import com.bridee.api.entity.VerificationToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Objects;

@Tag(name = "Controller de usuário")
public interface UsuarioController {

    @Operation(summary = "Busca um usuário pelo e-mail",
            description = "Busca um usuário pelo e-mail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o usuário"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    ResponseEntity<UsuarioResponseDto> findByEmail(@PathVariable String email);

    @Operation(summary = "Ativa a conta do usuário",
            description = "Ativa a conta do usuário pelo token de verificação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conta ativada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Token de validação expirado")
    })
    ResponseEntity<Void> activeAccount(@RequestParam String token, HttpServletResponse response) throws IOException;

    @Operation(summary = "Envia e-mail de verificação",
            description = "Envia e-mail de verificação do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "E-mail enviado com sucesso"),
            @ApiResponse(responseCode = "404", description = "E-mail não encontrado")
    })
    ResponseEntity<Void> resendVerificationEmail(@PathVariable String email);
}
