package com.bridee.api.dto.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UsuarioResponseDto {
    private int id;
    private String nome;
    private String email;
    private String telefone;
    private String estadoCivil;
}
