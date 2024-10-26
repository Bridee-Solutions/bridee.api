package com.bridee.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(name = "DTO do orçamento do fornecedor",
        description = "DTO para enviar as informações do orcamento do fornecedor")
public class FornecedorOrcamentoRequestDto {

    private Integer id;
    @NotBlank
    @Schema(description = "Nome do orcamento", example = "Vestido")
    private String nome;
    @NotNull
    @Positive
    @Schema(description = "Valor cobrado para um casal", example = "300.0")
    private BigDecimal preco;
    @NotNull
    @Positive
    @Schema(description = "Id do fornecedor", example = "2")
    private Integer fornecedorId;
    @NotNull
    @Positive
    @Schema(description = "Id do casal", example = "3")
    private Integer casalId;

}
