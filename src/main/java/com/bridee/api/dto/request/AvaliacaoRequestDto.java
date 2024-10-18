package com.bridee.api.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AvaliacaoRequestDto {

    @Positive
    @NotNull
    @Min(1)
    @Max(5)
    private Integer nota;
    @Positive
    @NotNull
    private Integer casalId;
    @Positive
    private Integer fornecedorId;
    @Positive
    private Integer assessorId;

}
