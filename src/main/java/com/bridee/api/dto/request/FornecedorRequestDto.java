package com.bridee.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(name = "DTO de fornecedor",
        description = "DTO para enviar informações do fornecedor")
public class FornecedorRequestDto {

    private Integer id;
    @Size(min = 3)
    @NotBlank
    @Schema(description = "Nome do Fornecedor", example = "Clodoaldo")
    private String nome;
    @Email
    @Schema(description = "E-mail do fornecedor", example = "clodoaldo@example.com")
    private String email;

}
