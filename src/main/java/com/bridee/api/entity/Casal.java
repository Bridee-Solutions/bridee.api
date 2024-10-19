package com.bridee.api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Casal extends Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nomeParceiro;
    private String telefoneParceiro;
    private String endereco;
    private String cep;
    private String foto;
    private BigDecimal orcamentoTotal;

    @ManyToOne
    @JoinColumn
    private Assessor assessor;

    @OneToMany(mappedBy = "casal")
    private List<ItemOrcamento> itemOrcamentos = new ArrayList<>();

    @OneToMany(mappedBy = "casal")
    private List<OrcamentoFornecedor> orcamentoFornecedores = new ArrayList<>();
}
