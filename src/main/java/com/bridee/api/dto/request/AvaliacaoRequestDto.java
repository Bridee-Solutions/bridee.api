package com.bridee.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(name = "DTO de avaliação",
        description = "DTO para envia informações sobre a avaliação")
public class AvaliacaoRequestDto {

    @Positive
    @NotNull
    @Min(1)
    @Max(5)
    @Schema(description = "nota para o associado", example = "3")
    private Integer nota;
    @Positive
    @NotNull
    @Schema(description = "Id do casal que realizou a avaliação", example = "2")
    private Integer casalId;
    @Positive
    @Schema(description = "Id do fornecedor a ser avaliado", example = "1")
    private Integer fornecedorId;
    @Positive
    @Schema(description = "Id do assessor a ser avaliado", example = "3")
    private Integer assessorId;

}
