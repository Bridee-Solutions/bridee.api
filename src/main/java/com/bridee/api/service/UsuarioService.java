package com.bridee.api.service;

import com.bridee.api.dto.security.SecurityUser;
import com.bridee.api.entity.Usuario;
import com.bridee.api.entity.VerificationToken;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.mapper.response.UsuarioResponseMapper;
import com.bridee.api.repository.UsuarioRoleRepository;
import com.bridee.api.repository.projection.usuario.UserDetailsProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bridee.api.repository.UsuarioRepository;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository repository;
    private final UsuarioRoleRepository usuarioRoleRepository;
    private final VerificationTokenService verificationTokenService;
    private final UsuarioResponseMapper responseMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetailsProjection projection = repository.findAllProjectedByEmail(username)
                .orElseThrow(ResourceNotFoundException::new);
        Usuario usuario = responseMapper.fromProjection(projection);
        return new SecurityUser(usuario, usuarioRoleRepository);
    }

    @Transactional(readOnly = true)
    public Usuario findByEmail(String email){
        return repository.findByEmail(email).orElseThrow(ResourceNotFoundException::new);
    }

    public void validateUser(VerificationToken verificationToken){
        verificationTokenService.confirmVerificationToken(verificationToken);
        Usuario usuario = verificationToken.getUsuario();
        usuario.setEnabled(true);
        repository.save(usuario);
    }


}
