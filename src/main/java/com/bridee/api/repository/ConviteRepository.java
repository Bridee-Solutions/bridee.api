package com.bridee.api.repository;

import com.bridee.api.entity.Convite;

import com.bridee.api.projection.orcamento.RelatorioProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;


public interface ConviteRepository extends JpaRepository<Convite, Integer>, JpaSpecificationExecutor<Convite> {

    boolean existsByNomeAndCasamentoId(String nome, Integer casamentoId);

    @Query("""
            SELECT COUNT(c) as convidadosCadastrados,
            (SELECT COUNT(co) FROM Convidado co WHERE co.convite.casamento.id = :casamentoId AND co.status="SEM RESPOSTA") as semResposta,
            (SELECT COUNT(co) FROM Convidado co WHERE co.convite.casamento.id = :casamentoId AND co.status="CONFIRMADO") as confirmado,
            (SELECT COUNT(co) FROM Convidado co WHERE co.convite.casamento.id = :casamentoId AND co.status="RECUSADO") as recusado
            FROM Convidado c
            WHERE c.convite.casamento.id = :casamentoId
            """)
    RelatorioProjection gerarRelatorio(Integer casamentoId);
}
