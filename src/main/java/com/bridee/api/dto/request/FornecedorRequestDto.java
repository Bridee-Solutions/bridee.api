package com.bridee.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.br.CNPJ;

@Data
public class FornecedorRequestDto {

    private Integer id;
    @Size(min = 3)
    @NotBlank
    private String nome;
    @CNPJ
    @NotBlank
    private String cnpj;
    @Email
    private String email;
    @PositiveOrZero
    @NotNull
    private Integer nota;

}
