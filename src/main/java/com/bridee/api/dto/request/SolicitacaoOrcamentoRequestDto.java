package com.bridee.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SolicitacaoOrcamentoRequestDto{

    @NotBlank
    @Size(min = 3)
    private String nome;
    @NotNull
    private LocalDate data;
    @NotBlank
    @Size(min = 13, max = 13)
    private String telefone;
    @Positive
    @NotNull
    private Integer qtdConvidados;
    @NotBlank
    private String messageCasal;
    @NotBlank
    @Email
    private String emailCasal;
    @Email
    @NotBlank
    private String emailEmpresaAssessor;

}