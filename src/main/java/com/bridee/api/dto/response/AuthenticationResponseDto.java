package com.bridee.api.dto.response;

import com.bridee.api.dto.request.AuthenticationRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(name = "Authentication response DTO",
        description = "DTO para enviar os tokens de autenticação do usuário")
public class AuthenticationResponseDto {

    @Schema(description = "Token de acesso")
    private String accessToken;
    @Schema(description = "Refresh Token")
    private String refreshToken;
    @Schema(description = "Se o usuário está habilitado pela aplicação", example = "true")
    private Boolean enabled;
    private Integer casamentoId;
    private String tipoUsuario;

}