package com.bridee.api.dto.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuarioRequestDto {

    private int id;
    @Size(min = 3)
    @NotBlank
    private String nome;
    @Email
    private String email;
    @Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")
    @NotBlank
    private String senha;
    @Size(max = 11, min = 11)
    private String telefone;
    private String estadoCivil;
}
