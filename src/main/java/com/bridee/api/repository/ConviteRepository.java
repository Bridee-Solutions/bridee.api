package com.bridee.api.repository;

import com.bridee.api.entity.Convidado;
import com.bridee.api.entity.Convite;

import com.bridee.api.entity.enums.CategoriaConvidadoEnum;
import com.bridee.api.entity.enums.TipoConvidado;
import com.bridee.api.projection.convite.CategoriaConvidadoProjection;
import com.bridee.api.projection.convite.ConviteResumoProjection;
import com.bridee.api.projection.orcamento.RelatorioProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


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

    @Query("""
            SELECT c.pin FROM Convite c WHERE c.casamento.id = :casamentoId
            """)
    List<String> findAllPinByCasamentoId(Integer casamentoId);

    @Query("""
            SELECT c FROM Convidado c WHERE c.convite.id = :conviteId AND c.tipo = :tipo
            """)
    Convidado findTitularConvite(Integer conviteId, TipoConvidado tipo);

    List<Convite> findAllByCasamentoId(Integer casamentoId);

    @Query("""
            SELECT COUNT(c) as totalConvites,
            (SELECT COUNT(co) FROM Convidado co WHERE co.convite.casamento.id = :casamentoId) as totalConvidados,
            (SELECT COUNT(co) FROM Convidado co WHERE co.convite.casamento.id = :casamentoId AND co.status = "CONFIRMADO") as totalConfirmado,
            (SELECT COUNT(co) FROM Convidado co WHERE co.convite.casamento.id = :casamentoId AND co.faixaEtaria = "ADULTO") as totalAdultos,
            (SELECT COUNT(co) FROM Convidado co WHERE co.convite.casamento.id = :casamentoId AND co.faixaEtaria = "CRIANCA") as totalCriancas
            FROM Convite c WHERE c.casamento.id = :casamentoId
            """)
    ConviteResumoProjection resumoCasamentoInvites(Integer casamentoId);

    @Query("""
            SELECT
            (SELECT COUNT(co) FROM Convidado co WHERE co.categoriaConvidado.nome = :categoriaConvidado AND co.convite.id = c.id) as total
            FROM Convite c WHERE c.casamento.id = :casamentoId
            """)
    CategoriaConvidadoProjection resumoCategoriaInvite(Integer casamentoId, CategoriaConvidadoEnum categoriaConvidado);
}
