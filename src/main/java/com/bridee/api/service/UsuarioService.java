package com.bridee.api.service;

import com.bridee.api.dto.security.SecurityUser;
import com.bridee.api.entity.Usuario;
import com.bridee.api.entity.VerificationToken;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.repository.UsuarioRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bridee.api.repository.UsuarioRepository;


@Service
@RequiredArgsConstructor
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository repository;
    private final UsuarioRoleRepository usuarioRoleRepository;
    private final VerificationTokenService verificationTokenService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = repository.findByEmail(username).orElseThrow(ResourceNotFoundException::new);
        return new SecurityUser(usuario, usuarioRoleRepository);
    }

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
