package com.bridee.api.configuration;

import com.bridee.api.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (isNonFilteredRequest(request)){
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = getJwtFromRequest(request);
        if(Objects.isNull(jwt)){
            filterChain.doFilter(request, response);
            return;
        }
        SecurityContext context = SecurityContextHolder.getContext();
        String email = jwtService.extractUsername(jwt);
        if (email != null && context.getAuthentication() == null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            if (jwtService.isTokenValid(jwt, userDetails)){
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request){
        String jwt = getJwtFromCookie(request);
        if(Objects.isNull(jwt)){
            String headerValue = getJwtFromHeaders(request);
            if(Objects.nonNull(headerValue)){
                jwt = headerValue.substring(7);
            }
        }
        return jwt;
    }

    private String getJwtFromHeaders(HttpServletRequest request){
        return request.getHeader("Bridee-Token");
    }

    private String getJwtFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;
        for (Cookie cookie: cookies){
            if (cookie.getName().equals("access_token")){
                if(cookie.getValue() == null) return null;
                return cookie.getValue();
            }
        }
        return null;
    }
    
    private boolean isNonFilteredRequest(HttpServletRequest request){
        String method = request.getMethod();
        if(method.equals("POST")
                && (request.getServletPath().equals("/assessores")
                || request.getServletPath().equals("/casais")
                || request.getServletPath().equals("/authentication"))){
            return true;
        }
        return false;
    }
}
