package com.bridee.api.dto.security;

import com.bridee.api.entity.Usuario;
import com.bridee.api.entity.UsuarioRole;
import com.bridee.api.entity.enums.RoleEnum;
import com.bridee.api.repository.UsuarioRoleRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@AllArgsConstructor
public class SecurityUser implements UserDetails {

    private final Usuario usuario;
    private UsuarioRoleRepository usuarioRoleRepository;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<RoleEnum> usuarioRoles = usuarioRoleRepository.findRoleNameByUsuarioEmail(usuario.getEmail());
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
        return usuario.getEnabled();
    }
}
