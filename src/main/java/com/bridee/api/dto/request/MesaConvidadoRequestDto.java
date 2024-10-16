package com.bridee.api.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class MesaConvidadoRequestDto {

    @Positive
    @NotNull
    private Integer mesaId;
    @Positive
    @NotNull
    private Integer convidadoId;

}
