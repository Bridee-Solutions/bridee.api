package com.bridee.api.repository;

import com.bridee.api.entity.Assessor;
import com.bridee.api.projection.associado.AssociadoResponseProjection;
import com.bridee.api.projection.associado.AssociadoGeralResponseProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface AssessorRepository extends JpaRepository<Assessor, Integer> {

    boolean existsByCnpjOrEmail(String cnpj, String email);

    boolean existsByCnpj(String cnpj);
    boolean existsByEmailEmpresa(String emailEmpresa);

    @Query("""
            SELECT ifs.assessor.nome as nome,
            ifs.assessor.id as id,
            ifs.visaoGeral as visaoGeral,
            ifs.cidade as cidade,
            ifs.bairro as bairro,
            (SELECT AVG(a.nota) FROM Avaliacao a WHERE a.assessor.id = :id) as notaMedia,
            (SELECT COUNT(a) FROM Avaliacao a WHERE a.assessor.id = :id) as totalAvaliacoes,
            ifs.urlSite as siteUrl,
            ifs.servicosOferecidos as servicosFornecidos,
            ifs.formaDeTrabalho as formaDeTrabalho,
            ifs.tamanhoCasamento as qtdConvidados,
            ifs.casamentosCatolicos as isCasamentoCatolico
            FROM InformacaoAssociado ifs WHERE ifs.assessor.id = :id
            """)
    AssociadoGeralResponseProjection findFornecedorInformations(Integer id);

    @Query("""
            SELECT ifs.assessor.nome as nome,
            ifs.assessor.id as id,
            ifs.visaoGeral as visaoGeral,
            ifs.cidade as cidade,
            ifs.bairro as bairro,
            (SELECT AVG(a.nota) FROM Avaliacao a WHERE a.assessor.id = ifs.assessor.id) as notaMedia,
            (SELECT COUNT(a) FROM Avaliacao a WHERE a.assessor.id = ifs.assessor.id) as totalAvaliacoes
            FROM InformacaoAssociado ifs
            """)
    Page<AssociadoResponseProjection> findAssessorDetails(Pageable pageable);

    @Query("""
            SELECT a FROM Assessor a WHERE UPPER(nome) LIKE UPPER(concat('%',:nome,'%'))
            """)
    Page<Assessor> findAllByNome(String nome, Pageable pageable);
}
