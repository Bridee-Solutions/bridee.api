package com.bridee.api.service;

import com.bridee.api.dto.security.SecurityUser;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.repository.UsuarioRoleRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bridee.api.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository repository;
    private final UsuarioRoleRepository usuarioRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new SecurityUser(repository.findByEmail(username).orElseThrow(ResourceNotFoundException::new), usuarioRoleRepository);
    }
}
