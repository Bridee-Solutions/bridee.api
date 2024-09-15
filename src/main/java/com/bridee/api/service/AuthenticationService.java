package com.bridee.api.service;

import com.bridee.api.dto.request.AuthenticationRequestDto;
import com.bridee.api.dto.response.AuthenticationResponseDto;
import com.bridee.api.dto.security.SecurityUser;
import com.bridee.api.entity.Usuario;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;

    private AuthenticationResponseDto authenticate(AuthenticationRequestDto authenticationRequest){
        Usuario usuario = usuarioRepository.findByEmailAndSenha(authenticationRequest.getEmail(), authenticationRequest.getPassword()).orElseThrow(ResourceNotFoundException::new);
        String accessToken = jwtService.generateToken(new HashMap<>(), new SecurityUser(usuario));
        String refreshToken = jwtService.generateRefreshToken(new SecurityUser(usuario));
        return AuthenticationResponseDto
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}
