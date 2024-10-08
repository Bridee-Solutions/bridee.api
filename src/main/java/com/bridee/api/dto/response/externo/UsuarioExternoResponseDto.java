package com.bridee.api.dto.response.externo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class UsuarioExternoResponseDto {

    private int id;
    private String nome;
    private String email;
    private String telefone;
    private String estadoCivil;

}
