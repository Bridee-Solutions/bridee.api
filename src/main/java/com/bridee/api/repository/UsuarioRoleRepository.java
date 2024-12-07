package com.bridee.api.repository;

import com.bridee.api.entity.Usuario;
import com.bridee.api.entity.UsuarioRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRoleRepository extends JpaRepository<UsuarioRole, Integer> {

    List<UsuarioRole> findByUsuario(Usuario usuario);
    List<UsuarioRole> findByUsuarioEmail(String email);
}
