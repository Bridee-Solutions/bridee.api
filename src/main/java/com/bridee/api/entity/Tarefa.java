package com.bridee.api.entity;

import com.bridee.api.entity.enums.TarefaCategoriaEnum;
import com.bridee.api.entity.enums.TarefaStatusEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tarefa {

    // TODO: tirar meses anteriores e trocar dataLimite por data.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer mesesAnteriores;
    private String nome;
    private String descricao;
    @Enumerated(EnumType.STRING)
    private TarefaCategoriaEnum categoria;
    @Enumerated(EnumType.STRING)
    private TarefaStatusEnum status;
    private LocalDate dataLimite;

    @OneToMany(mappedBy = "tarefa")
    private List<TarefaCasal> tarefaCasals;

}
