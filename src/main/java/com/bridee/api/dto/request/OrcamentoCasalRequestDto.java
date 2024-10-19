package com.bridee.api.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Data
public class OrcamentoCasalRequestDto {

    @NotNull
    @Positive
    private Integer casalId;
    @NotNull
    private List<@NotNull ItemOrcamentoRequestDto> itemOrcamentos;
    @NotNull
    private List<@NotNull FornecedorOrcamentoRequestDto> orcamentoFornecedores;

}
