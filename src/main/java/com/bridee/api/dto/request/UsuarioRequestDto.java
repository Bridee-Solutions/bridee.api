package com.bridee.api.dto.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UsuarioRequestDto {


    private int id;
    private String nome;
    @Email
    private String email;
    @Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")
    private String senha;
    private String telefone;
    private String estadoCivil;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
