package com.bridee.api.entity;

import com.bridee.api.entity.enums.PedidoAssessoriaStatusEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoAssessoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private BigDecimal preco;

    @Enumerated(EnumType.STRING)
    private PedidoAssessoriaStatusEnum status;

    @OneToOne
    @JoinColumn(name = "casamento_id")
    private Casamento casamento;

    @ManyToOne
    private Assessor assessor;

    public PedidoAssessoria(PedidoAssessoriaStatusEnum status, Casamento casamento, Assessor assessor) {
        this.status = status;
        this.casamento = casamento;
        this.assessor = assessor;
    }
}
