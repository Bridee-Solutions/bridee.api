package com.bridee.api.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsuarioRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Usuario usuario;

    public String getUserRole(){
        return role.roleName();
    }
}