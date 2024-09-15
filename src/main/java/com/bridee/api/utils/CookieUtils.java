package com.bridee.api.utils;

import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;

import java.time.Duration;

public class CookieUtils {

    public static HttpCookie createCookie(String name, String value, boolean secure, Duration maxAge, String domain){
        return ResponseCookie.from(name)
                .value(value)
                .secure(secure)
                .maxAge(maxAge)
                .domain(domain)
                .path("/")
                .httpOnly(true)
                .build();
    }

    public static HttpCookie clearCookie(String name, String domain){
        return ResponseCookie.from(name)
                .value("")
                .httpOnly(true)
                .maxAge(1)
                .domain(domain)
                .path("/")
                .build();
    }
}
