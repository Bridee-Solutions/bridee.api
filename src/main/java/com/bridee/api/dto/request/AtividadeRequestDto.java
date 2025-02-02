package com.bridee.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AtividadeRequestDto {

    @NotBlank
    private String nome;
    @NotNull(message = "Data de inicio não pode ser nula")
    private LocalDateTime inicio;
    @NotNull
    private LocalDateTime fim;

}
