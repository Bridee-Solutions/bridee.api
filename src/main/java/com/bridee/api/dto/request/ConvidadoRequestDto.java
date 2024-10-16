package com.bridee.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ConvidadoRequestDto {

    @NotBlank
    private String nome;
    @NotBlank
    private String sobrenome;
    @NotBlank
    private String telefone;
    @NotBlank
    private String categoria;
    @NotBlank
    private String faixaEtaria;

}
