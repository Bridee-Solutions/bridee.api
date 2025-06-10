package com.bridee.api.repository;

import com.bridee.api.entity.Usuario;
import com.bridee.api.entity.UsuarioRole;
import com.bridee.api.entity.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsuarioRoleRepository extends JpaRepository<UsuarioRole, Integer> {

    @Query("""
            SELECT ur.role.nome FROM UsuarioRole ur WHERE ur.usuario.email = :email
            """)
    List<RoleEnum> findRoleNameByUsuarioEmail(String email);
}
