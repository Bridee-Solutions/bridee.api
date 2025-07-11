package com.bridee.api.controller.impl;

import com.bridee.api.controller.AuthenticationController;
import com.bridee.api.dto.request.AuthenticationRequestDto;
import com.bridee.api.dto.response.AuthenticationResponseDto;
import com.bridee.api.dto.response.RefreshTokenResponse;
import com.bridee.api.service.AuthenticationService;
import com.bridee.api.utils.CookieUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthenticationControllerImpl implements AuthenticationController {

    @Value("${httpOnly.cookie.host}")
    private String host;
    private final AuthenticationService authenticationService;

    @PostMapping("/authentication")
    public ResponseEntity<AuthenticationResponseDto> authenticate(@RequestBody @Valid AuthenticationRequestDto authenticationRequest){
        log.info("AUTENTICAÇÃO: autenticando o usuario com e-mail: {}", authenticationRequest.getEmail());
        AuthenticationResponseDto authenticationResponse = authenticationService.authenticate(authenticationRequest);
        HttpCookie accessTokenCookie = CookieUtils.createCookie("access_token", authenticationResponse.getAccessToken(), true, Duration.ofHours(1),host);
        HttpCookie refreshTokenCookie = CookieUtils.createCookie("refresh_token", authenticationResponse.getRefreshToken(), true, Duration.ofDays(7), host);

        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
        headers.add(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
        return new ResponseEntity<>(authenticationResponse, headers, HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponse> refreshToken(HttpServletRequest request){
        String refreshToken = authenticationService.extractRefreshTokenFromRequest(request);
        String accessToken = authenticationService.generateNewAccessToken(refreshToken);

        HttpCookie accessTokenCookie = CookieUtils.createCookie("access_token", accessToken, true, Duration.ofHours(1), host);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
        return new ResponseEntity<>(new RefreshTokenResponse(accessToken), headers, HttpStatus.OK);
    }

}
