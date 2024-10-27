package com.bridee.api.controller.impl;

import com.bridee.api.controller.AuthenticationController;
import com.bridee.api.dto.request.AuthenticationRequestDto;
import com.bridee.api.dto.response.AuthenticationResponseDto;
import com.bridee.api.service.AuthenticationService;
import com.bridee.api.utils.CookieUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthenticationControllerImpl implements AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/authentication")
    public ResponseEntity<AuthenticationResponseDto> authenticate(@RequestBody @Valid AuthenticationRequestDto authenticationRequest){
        AuthenticationResponseDto authenticationResponse = authenticationService.authenticate(authenticationRequest);
        HttpCookie accessTokenCookie = CookieUtils.createCookie("access_token", authenticationResponse.getAccessToken(), true, Duration.ofHours(1),"localhost");
        HttpCookie refreshTokenCookie = CookieUtils.createCookie("refresh_token", authenticationResponse.getRefreshToken(), true, Duration.ofDays(7), "localhost");
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
        headers.add(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
        return new ResponseEntity<>(authenticationResponse, headers, HttpStatus.OK);
    }

}
