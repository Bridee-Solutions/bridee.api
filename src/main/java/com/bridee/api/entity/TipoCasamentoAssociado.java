package com.bridee.api.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TipoCasamentoAssociado {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private TipoCasamento tipoCasamento;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "informacao_associado_id")
    private InformacaoAssociado informacaoAssociado;
    
}
