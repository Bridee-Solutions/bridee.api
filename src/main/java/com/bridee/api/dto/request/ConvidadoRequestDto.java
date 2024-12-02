package com.bridee.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(name = "DTO de request do Convidado",
        description = "DTO para enviar as informações de casal")
public class ConvidadoRequestDto {

    @NotBlank
    @Schema(description = "Nome do convidado", example = "Clodoaldo")
    private String nome;
    @NotBlank
    @Schema(description = "Sobrenome do convidado", example = "Bernardes")
    private String sobrenome;
    @NotBlank
    @Schema(description = "Telefone do convidado", example = "+xx (xx) xxxxxxxxx")
    private String telefone;
    private String tipo;
    private String status;
    @NotBlank
    @Schema(description = "Faixa etaria do convidado", example = "ADULTO")
    private String faixaEtaria;

    private Integer mesaId;
    private Integer conviteId;
    private CategoriaConvidadoRequestDto categoriaConvidado;
}
