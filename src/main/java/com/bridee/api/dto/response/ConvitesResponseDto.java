package com.bridee.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Schema(name = "DTO de resposta do convite e seus convidados",
        description = "DTO para devolver o convite e todos os convidados de um casamento")
public class ConvitesResponseDto {

    private Integer id;
    @Schema(description = "Nome do convite", example = "Familia Forbes")
    private String nome;
    @Schema(description = "Pin do convite", example = "556611")
    private String pin;
    @Schema(description = "Convidados do convite")
    private List<ConvidadoResponseDto> convidados = new ArrayList<>();

}
