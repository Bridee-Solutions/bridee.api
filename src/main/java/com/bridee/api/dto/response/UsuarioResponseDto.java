package com.bridee.api.dto.response;

import lombok.Data;

@Data
public class UsuarioResponseDto {
    private int id;
    private String nome;
    private String email;
    private String telefone;
    private String estadoCivil;
    private Boolean enabled;
}
