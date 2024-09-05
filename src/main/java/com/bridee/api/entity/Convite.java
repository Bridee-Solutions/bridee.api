package com.bridee.api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
public class Convite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @OneToMany
    @JoinColumn
    private Integer convidadoId;
    
    @OneToMany
    @JoinColumn
    private Integer eventoId;
    
    @OneToMany
    @JoinColumn
    private Integer acompanhanteId;
}
