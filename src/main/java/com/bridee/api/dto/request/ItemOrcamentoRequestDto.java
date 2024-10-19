package com.bridee.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Data
public class ItemOrcamentoRequestDto {

    private Integer id;
    @NotBlank
    private String tipo;
    @Positive
    @NotNull
    private Integer casalId;
    @NotNull
    private List<@NotNull CustoItemOrcamentoRequestDto> custos;

}
