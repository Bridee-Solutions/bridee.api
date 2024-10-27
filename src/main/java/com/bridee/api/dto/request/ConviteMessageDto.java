package com.bridee.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "DTO de convite",
        description = "DTO para enviar as mensagens de um convite")
public class ConviteMessageDto {

    @NotNull
    @Positive
    @Schema(description = "Id do casamento", example = "1")
    private Integer casamentoId;
    @NotNull
    @Positive
    @Schema(description = "Id do convite", example = "2")
    private Integer conviteId;
    @NotNull
    @Schema(description = "Id's dos convidados", example = "[1,2,4]")
    private List<@NotNull Integer> convidadosIds;

}
