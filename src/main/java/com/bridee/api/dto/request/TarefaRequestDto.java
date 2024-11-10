package com.bridee.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TarefaRequestDto {

    @NotBlank
    private String nome;
    @NotBlank
    private String descricao;
    @NotBlank
    private String categoria;
    @NotBlank
    private String status;
    @NotNull
    private LocalDate dataLimite;
    private Integer mesesAnteriores;
}
