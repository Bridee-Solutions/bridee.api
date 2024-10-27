package com.bridee.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Schema(name = "DTO de mesa",
        description = "DTO para enviar os dados da mesa e seu respectivo convidado")
public class MesaConvidadoRequestDto {

    @Positive
    @NotNull
    @Schema(description = "Id da mesa", example = "1")
    private Integer mesaId;
    @Positive
    @NotNull
    @Schema(description = "Id do convidado", example = "2")
    private Integer convidadoId;

}
