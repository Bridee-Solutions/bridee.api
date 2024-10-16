package com.bridee.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class MesaRequestDto {

    @NotBlank
    private String nome;
    @PositiveOrZero
    private Integer numeroAssentos;

}
