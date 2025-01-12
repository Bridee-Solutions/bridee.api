package com.bridee.api.configuration;

import com.bridee.api.repository.UsuarioRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class CustomJwtConverter implements Converter<Jwt, JwtAuthenticationToken> {

    private UsuarioRoleRepository usuarioRoleRepository;

    @Override
    public JwtAuthenticationToken convert(Jwt source) {
        String userEmail = source.getClaim("email");
        List<SimpleGrantedAuthority> authorities = usuarioRoleRepository.findByUsuarioEmail(userEmail).stream()
                .map(usuarioRole -> new SimpleGrantedAuthority(usuarioRole.getUserRole())).toList();
        return new JwtAuthenticationToken(source, authorities);
    }

}
