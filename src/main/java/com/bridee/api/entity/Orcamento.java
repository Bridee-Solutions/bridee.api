package com.bridee.api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Orcamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn
    private CasalAssessor casalAssessor;

    @ManyToOne
    @JoinColumn
    private ItemOrcamento itemOrcamento;

    @ManyToOne
    @JoinColumn
    private OrcamentoFornecedor orcamentoFornecedor;
}
