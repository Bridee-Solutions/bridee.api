package com.bridee.api.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Data
public class ConviteMessageDto {

    @NotNull
    @Positive
    private Integer casamentoId;
    @NotNull
    @Positive
    private Integer conviteId;
    @NotNull
    private List<@NotNull Integer> convidadosIds;

}
