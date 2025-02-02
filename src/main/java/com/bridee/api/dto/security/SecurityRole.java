package com.bridee.api.dto.security;

import com.bridee.api.entity.Usuario;
import com.bridee.api.entity.UsuarioRole;
import com.bridee.api.entity.enums.RoleEnum;
import com.bridee.api.repository.UsuarioRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

public class SecurityRole {

    public static Collection<? extends GrantedAuthority> findAllUserRoles(List<RoleEnum> usuarioRoles){
        return usuarioRoles.stream().map(usuario -> new SimpleGrantedAuthority(usuario.name())).toList();
    }

}
