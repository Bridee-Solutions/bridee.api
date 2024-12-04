package com.bridee.api.service;

import com.bridee.api.dto.request.AuthenticationRequestDto;
import com.bridee.api.dto.response.AuthenticationResponseDto;
import com.bridee.api.dto.security.SecurityUser;
import com.bridee.api.entity.Casal;
import com.bridee.api.entity.Usuario;
import com.bridee.api.entity.enums.UsuarioEnum;
import com.bridee.api.exception.BadRequestEntityException;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.exception.UnauthorizedUserException;
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
    private final CasamentoService casamentoService;

    public AuthenticationResponseDto authenticate(AuthenticationRequestDto requestDto){
        Usuario usuario = usuarioRepository.findByEmail(requestDto.getEmail()).orElseThrow(() -> new ResourceNotFoundException("Usuário Inválido"));
        UserDetails userAuthenticated = new SecurityUser(usuario);
        if (!passwordEncoder.matches(requestDto.getSenha(), userAuthenticated.getPassword())){
            throw new ResourceNotFoundException("Usuário inválido");
        }
        if (usuario.getExterno()){
            throw new UsuarioExternoException("Usuário não cadastro pela aplicação");
        }
        return buildAuthenticationResponse(usuario, userAuthenticated);

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
                .casamentoId(findCasamentoId(usuario, userType))
                .enabled(usuario.getEnabled())
                .build();
    }

    private Integer findCasamentoId(Usuario usuario, UsuarioEnum userType){
         return isCasalUser(userType) ? casamentoService.getCasamentoId(usuario.getId()) : null;
    }

    private boolean isCasalUser(UsuarioEnum userType){
        return userType.equals(UsuarioEnum.CASAL);
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
            throw new UnauthorizedUserException("Refresh Token inválido");
        }
        return jwtService.generateToken(new HashMap<>(), userDetails);
    }

    private UserDetails createUserDetails(String refreshToken){
        String username = jwtService.extractUsername(refreshToken);
        Usuario usuario = usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));
        return new SecurityUser(usuario);
    }

}
