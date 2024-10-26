package com.bridee.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "Orcamento DTO",
        description = "DTO para enviar os dados de um orçamento")
public class OrcamentoCasalRequestDto {

    @NotNull
    @Positive
    private Integer casalId;
    @NotNull
    private List<@NotNull ItemOrcamentoRequestDto> itemOrcamentos;
    @NotNull
    private List<@NotNull FornecedorOrcamentoRequestDto> orcamentoFornecedores;

}
