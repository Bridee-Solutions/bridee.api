package com.bridee.api.repository;

import java.util.Optional;

import com.bridee.api.repository.projection.usuario.UserDetailsProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bridee.api.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

    Optional<Usuario> findByEmail(String email);

    Optional<UserDetailsProjection> findAllProjectedByEmail(String email);
}
