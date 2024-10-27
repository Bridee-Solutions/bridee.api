package com.bridee.api.service;

import com.bridee.api.dto.request.AuthenticationRequestDto;
import com.bridee.api.dto.response.AuthenticationResponseDto;
import com.bridee.api.dto.security.SecurityUser;
import com.bridee.api.entity.Usuario;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.exception.UsuarioExternoException;
import com.bridee.api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationResponseDto authenticate(AuthenticationRequestDto requestDto){
        Usuario usuario = usuarioRepository.findByEmail(requestDto.getEmail()).orElseThrow(() -> new ResourceNotFoundException("Usuário Inválido"));
        UserDetails userAuthenticated = new SecurityUser(usuario);
        if (!passwordEncoder.matches(requestDto.getSenha(), userAuthenticated.getPassword())){
            throw new ResourceNotFoundException("Usuário inválido");
        }
        if (usuario.getExterno()){
            throw new UsuarioExternoException("Usuário não cadastro pela aplicação");
        }
        String accessToken = jwtService.generateToken(new HashMap<>(), userAuthenticated);
        String refreshToken = jwtService.generateRefreshToken(userAuthenticated);
        return AuthenticationResponseDto
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .enabled(usuario.getEnabled())
                .build();
    }

}
