package com.bridee.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(name = "DTO de custo de um item",
        description = "DTO para enviar as informações do custo de um item")
public class CustoItemOrcamentoRequestDto {


    private Integer id;
    @NotBlank
    @Schema(description = "Nome do custo", example = "Custo XPTO")
    private String nome;
    @NotNull
    @Positive
    @Schema(description = "Valor do custo", example = "200.0")
    private BigDecimal precoAtual;

}
