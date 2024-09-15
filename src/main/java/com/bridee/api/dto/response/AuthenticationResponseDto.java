package com.bridee.api.dto.response;

import com.bridee.api.dto.request.AuthenticationRequestDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponseDto {

    private String accessToken;
    private String refreshToken;

}
