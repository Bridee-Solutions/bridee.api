package com.bridee.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "DTO de mesa",
        description = "DTO para devolver as informações de mesa")
public class MesaResponseDto {

    private Integer id;
    @Schema(description = "Nome da mesa", example = "Mesa 1")
    private String nome;
    @Schema(description = "Numero de assentos", example = "4")
    private Integer numeroAssentos;
    @Schema(description = "Convidados alocados em uma mesa")
    private List<ConvidadoResponseDto> convidados;

}
