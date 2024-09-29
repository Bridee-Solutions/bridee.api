package com.bridee.api.dto.request.externo;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UsuarioExternoRequestDto {

    private int id;
    private String nome;
    @Email
    private String email;
    private String telefone;
    private String estadoCivil;
    private Boolean externo = true;
}
