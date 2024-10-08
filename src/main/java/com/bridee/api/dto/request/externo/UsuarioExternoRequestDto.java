package com.bridee.api.dto.request.externo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuarioExternoRequestDto {

    private int id;
    @Size(min = 3)
    @NotBlank
    private String nome;
    @Email
    private String email;
    @Size(max = 11, min = 11)
    private String telefone;
    private String estadoCivil;
    private Boolean externo = true;
}
