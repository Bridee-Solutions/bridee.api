package com.bridee.api.dto.security;

import com.bridee.api.entity.Usuario;
import com.bridee.api.entity.UsuarioRole;
import com.bridee.api.repository.UsuarioRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class SecurityUser implements UserDetails {

    private final Usuario usuario;
    @Autowired
    private UsuarioRoleRepository usuarioRoleRepository;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<UsuarioRole> usuarioRoles = usuarioRoleRepository.findByUsuario(usuario);
        return SecurityRole.findAllUserRoles(usuarioRoles);
    }

    @Override
    public String getPassword() {
        return usuario.getSenha();
    }

    @Override
    public String getUsername() {
        return usuario.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
