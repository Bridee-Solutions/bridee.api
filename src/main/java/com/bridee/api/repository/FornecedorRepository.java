package com.bridee.api.repository;

import com.bridee.api.entity.Fornecedor;
import com.bridee.api.repository.projection.associado.AssociadoGeralResponseProjection;
import com.bridee.api.repository.projection.associado.AssociadoResponseProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Integer>, JpaSpecificationExecutor<Fornecedor> {

    @Query("""
            SELECT f FROM Fornecedor f JOIN FETCH f.subcategoriaServico
            """)
    Page<Fornecedor> findAllFornecedores(Pageable pageable);

    @Query("""
            SELECT ifs.fornecedor.nome as nome,
            ifs.fornecedor.id as id,
            ifs.id as informacaoAssociadoId,
            ifs.visaoGeral as visaoGeral,
            ifs.cidade as cidade,
            ifs.bairro as bairro,
            (SELECT AVG(a.nota) FROM Avaliacao a WHERE a.fornecedor.id = ifs.fornecedor.id) as notaMedia,
            (SELECT COUNT(a) FROM Avaliacao a WHERE a.fornecedor.id = ifs.fornecedor.id) as totalAvaliacoes
            FROM InformacaoAssociado ifs WHERE ifs.fornecedor.subcategoriaServico.id = :subcategoriaId
            AND UPPER(ifs.fornecedor.nome) LIKE CONCAT('%',UPPER(:nomeFornecedor),'%')
            """)
    Page<AssociadoResponseProjection> findFornecedorDetailsBySubcategoriaAndNome(Integer subcategoriaId,
                                                                                 String nomeFornecedor,
                                                                                 Pageable pageable);

    @Query("""
            SELECT f
            FROM Fornecedor f
            WHERE UPPER(f.nome) LIKE CONCAT('%',UPPER(:nome),'%')
            AND f.subcategoriaServico.categoriaServico.id = :categoriaId
            """)
    Page<Fornecedor> findFornecedoresByCategoriaAndNome(Integer categoriaId, String nome,Pageable pageable);

    @Query("""
            SELECT ifs.fornecedor.nome as nome,
            ifs.fornecedor.id as id,
            ifs.visaoGeral as visaoGeral,
            ifs.cidade as cidade,
            ifs.bairro as bairro,
            (SELECT AVG(a.nota) FROM Avaliacao a WHERE a.fornecedor.id = :id) as notaMedia,
            (SELECT COUNT(a) FROM Avaliacao a WHERE a.fornecedor.id = :id) as totalAvaliacoes,
            ifs.urlSite as siteUrl,
            ifs.servicosOferecidos as servicosFornecidos,
            ifs.formaDeTrabalho as formaDeTrabalho,
            ifs.tamanhoCasamento as qtdConvidados
            FROM InformacaoAssociado ifs WHERE ifs.fornecedor.id = :id
            """)
    AssociadoGeralResponseProjection findFornecedorInformations(Integer id);

    Optional<Fornecedor> findByEmail(String email);
}
