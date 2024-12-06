package com.bridee.api.entity;

import com.bridee.api.entity.enums.TipoImagemAssociadoEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImagemAssociado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn
    private Imagem imagem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private InformacaoAssociado informacaoAssociado;

    @Enumerated(EnumType.STRING)
    private TipoImagemAssociadoEnum tipo;

}
