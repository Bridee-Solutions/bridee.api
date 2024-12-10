package com.bridee.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "DTO de item do orçamento",
        description = "DTO para enviar dados de um item do orcamento")
public class ItemOrcamentoRequestDto {

    private Integer id;
    @NotBlank
    @Schema(description = "Tipo do item", example = "Joia")
    private String tipo;
    @Positive
    @NotNull
    @Schema(description = "Id do casal", example = "2")
    private Integer casamentoId;
    @NotNull
    @Schema(description = "Custos do item")
    private List<@NotNull CustoItemOrcamentoRequestDto> custos;

}
