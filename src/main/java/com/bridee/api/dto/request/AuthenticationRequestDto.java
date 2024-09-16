package com.bridee.api.dto.request;

import lombok.Data;

@Data
public class AuthenticationRequestDto {

    private String email;
    private String senha;
}
