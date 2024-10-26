package com.bridee.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(name = "DTO para solicitar orçamento",
        description = "DTO para enviar a solicitação de um orçamento de um casal para um assessor")
public class SolicitacaoOrcamentoRequestDto{

    @NotBlank
    @Size(min = 3)
    @Schema(description = "Nome do casal", example = "Clodoaldo & Josefa")
    private String nome;
    @NotNull
    @Schema(description = "Data do casamento", example = "Data do casamento")
    private LocalDate data;
    @NotBlank
    @Size(min = 13, max = 13)
    @Schema(description = "Telefone do casal")
    private String telefone;
    @Positive
    @NotNull
    @Schema(description = "Quantidade de convidados", example = "400")
    private Integer qtdConvidados;
    @NotBlank
    @Schema(description = "Mensagem do casal")
    private String messageCasal;
    @NotBlank
    @Email
    @Schema(description = "Email do casal")
    private String emailCasal;
    @Email
    @NotBlank
    @Schema(description = "Email da empresa do assessor", example = "assessoriaxpto@example.com")
    private String emailEmpresaAssessor;

}