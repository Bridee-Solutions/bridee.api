package com.bridee.api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InformacaoAssociado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String visaoGeral;
    private String servicosOferecidos;
    private String formaDeTrabalho;
    private String tamanhoCasamento;
    private Boolean casamentosCatolicos;
    private String urlSite;
    private String local;
    @OneToOne
    @JoinColumn
    private Fornecedor fornecedor;
    @OneToOne
    @JoinColumn
    private Assessor assessor;

    @OneToMany(mappedBy = "informacaoAssociado")
    private List<ImagemAssociado> imagemAssociados = new ArrayList<>();

    @OneToMany(mappedBy = "informacaoAssociado")
    private List<FormaPagamentoAssociado> formaPagamentoAssociados = new ArrayList<>();

}
