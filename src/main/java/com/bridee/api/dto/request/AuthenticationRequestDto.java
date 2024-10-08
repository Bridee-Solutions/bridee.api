package com.bridee.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthenticationRequestDto {

    @Email
    private String email;
    @NotBlank
    private String senha;
}
