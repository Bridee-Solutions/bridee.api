package com.bridee.api.service;

import com.bridee.api.dto.request.AuthenticationRequestDto;
import com.bridee.api.dto.response.AuthenticationResponseDto;
import com.bridee.api.dto.security.SecurityUser;
import com.bridee.api.entity.Casal;
import com.bridee.api.entity.Usuario;
import com.bridee.api.entity.enums.UsuarioEnum;
import com.bridee.api.exception.BadRequestEntityException;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.exception.UsuarioExternoException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UsuarioService usuarioService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationResponseDto authenticate(AuthenticationRequestDto requestDto){
        Usuario usuario = usuarioService.findByEmail(requestDto.getEmail());
        UserDetails userAuthenticated = new SecurityUser(usuario);
        validatePasswords(requestDto, userAuthenticated);
        validateUser(usuario);
        log.info("AUTENTICAÇÃO: usuário autenticado com sucesso.");
        return buildAuthenticationResponse(usuario, userAuthenticated);
    }

    private void validatePasswords(AuthenticationRequestDto requestDto, UserDetails userAuthenticated){
        if (!passwordEncoder.matches(requestDto.getSenha(), userAuthenticated.getPassword())){
            log.error("USUÁRIO: credenciais informadas não são válidas");
            throw new ResourceNotFoundException("Usuário inválido");
        }
    }

    private void validateUser(Usuario usuario){
        if (usuario.getExterno()){
            log.error("AUTENTICAÇÃO: usuário não cadastrado pela aplicação.");
            throw new UsuarioExternoException("Usuário não cadastro pela aplicação");
        }
    }

    private AuthenticationResponseDto buildAuthenticationResponse(Usuario usuario, UserDetails userAuthenticated){
        String accessToken = jwtService.generateToken(new HashMap<>(), userAuthenticated);
        String refreshToken = jwtService.generateRefreshToken(userAuthenticated);
        UsuarioEnum userType = defineUsuarioType(usuario);
        return AuthenticationResponseDto
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tipoUsuario(userType.name())
                .enabled(usuario.getEnabled())
                .build();
    }

    private UsuarioEnum defineUsuarioType(Usuario usuario){
        if (usuario instanceof Casal){
            return UsuarioEnum.CASAL;
        }
        return UsuarioEnum.ASSESSOR;
    }

    public String generateNewAccessToken(String refreshToken){
        UserDetails userDetails = createUserDetails(refreshToken);
        boolean isTokenValid = jwtService.isTokenValid(refreshToken, userDetails);
        if (!isTokenValid){
            throw new BadRequestEntityException("Refresh Token inválido");
        }
        return jwtService.generateToken(new HashMap<>(), userDetails);
    }

    private UserDetails createUserDetails(String refreshToken){
        String username = jwtService.extractUsername(refreshToken);
        return usuarioService.loadUserByUsername(username);
    }

    public String extractRefreshTokenFromRequest(HttpServletRequest request){
        String refreshToken = extractRefreshTokenFromCookie(request);

        if(Objects.isNull(refreshToken)){
            refreshToken = extractRefreshTokenFromHeader(request);
        }

        if(Objects.isNull(refreshToken)){
            throw new ResourceNotFoundException("Refresh Token não encontrado");
        }
        return refreshToken;
    }

    private String extractRefreshTokenFromHeader(HttpServletRequest request){
        return request.getHeader("refresh-token");
    }

    private String extractRefreshTokenFromCookie(HttpServletRequest request){
        List<Cookie> cookies = Arrays.asList(request.getCookies());
        return cookies.stream()
                .filter(cookie -> cookie.getName().equals("refresh_token"))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
    }

}
