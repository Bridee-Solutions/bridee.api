package com.bridee.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
@Schema(name = "DTO de mesa",
        description = "DTO para enviar os dados de mesa")
public class MesaRequestDto {

    @NotBlank
    @Schema(description = "Nome da mesa", example = "Mesa 1")
    private String nome;
    @PositiveOrZero
    @Schema(description = "Numero de assentos", example = "5")
    private Integer numeroAssentos;

}
