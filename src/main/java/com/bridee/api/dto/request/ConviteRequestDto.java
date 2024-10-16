package com.bridee.api.dto.request;

import com.bridee.api.entity.Convidado;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ConviteRequestDto {

    @NotBlank
    private String nome;
    @NotBlank
    private String telefoneTitular;
    @Positive
    @NotNull
    private Integer casamentoId;
    private List<ConvidadoRequestDto> convidados = new ArrayList<>();

}
