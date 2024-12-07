package com.bridee.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "DTO de resposta de convite",
        description = "DTO para devolver as informações de um convite")
public class ConviteResponseDto {

    private String id;
    @Schema(description = "Nome do convite", example = "Familia Forbes")
    private String nome;
    @Schema(description = "Pin do convite", example = "556611")
    private String pin;
    private String telefoneTitular;

}
