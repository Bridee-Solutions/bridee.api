package com.bridee.api.dto.request;

import com.bridee.api.entity.Convidado;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Schema(name = "Convite DTO",
        description = "DTO para enviar as informações do convite")
public class ConviteRequestDto {

    @NotBlank
    @Schema(description = "Nome do convite", example = "Familia Forbes")
    private String nome;
    @Schema(description = "Telefone do titular", example = "55119770531")
    private String telefoneTitular;
    @Positive
    @NotNull
    @Schema(description = "Id do casamento", example = "1")
    private Integer casamentoId;
    private Integer pin;
    @Schema(description = "Informações dos convidados a serem associados a um convite")
    private List<ConvidadoRequestDto> convidados = new ArrayList<>();

}
