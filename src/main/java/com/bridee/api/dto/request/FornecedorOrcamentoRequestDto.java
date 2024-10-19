package com.bridee.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class FornecedorOrcamentoRequestDto {

    private Integer id;
    @NotBlank
    private String nome;
    @NotNull
    @Positive
    private BigDecimal preco;
    @NotNull
    @Positive
    private Integer fornecedorId;
    @NotNull
    @Positive
    private Integer casalId;

}
