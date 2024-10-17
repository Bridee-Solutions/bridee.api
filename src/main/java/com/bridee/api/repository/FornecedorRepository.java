package com.bridee.api.repository;

import com.bridee.api.entity.Fornecedor;
import com.bridee.api.projection.fornecedor.FornecedorGeralResponseProjection;
import com.bridee.api.projection.fornecedor.FornecedorResponseProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Integer> {

    @Query("""
            SELECT ifs.fornecedor.nome as nome,
            ifs.fornecedor.id as id,
            ifs.visaoGeral as visaoGeral,
            ifs.local as local,
            (SELECT AVG(a.nota) FROM Avaliacao a WHERE a.fornecedor.id = ifs.fornecedor.id GROUP BY a.nota) as notaMedia,
            (SELECT COUNT(a) FROM Avaliacao a WHERE a.fornecedor.id = ifs.fornecedor.id GROUP BY a) as totalAvaliacoes
            FROM InformacaoAssociado ifs WHERE ifs.fornecedor.subcategoriaServico.id = :subcategoriaId
            """)
    Page<FornecedorResponseProjection> findFornecedorDetailsBySubcategoria(Integer subcategoriaId, Pageable pageable);

    @Query("""
            SELECT ifs.fornecedor.nome as nome,
            ifs.fornecedor.id as id,
            ifs.visaoGeral as visaoGeral,
            ifs.local as local,
            (SELECT AVG(a.nota) FROM Avaliacao a WHERE a.fornecedor.id = ifs.fornecedor.id GROUP BY a.nota) as notaMedia,
            (SELECT COUNT(a) FROM Avaliacao a WHERE a.fornecedor.id = ifs.fornecedor.id GROUP BY a) as totalAvaliacoes
            FROM InformacaoAssociado ifs WHERE ifs.fornecedor.subcategoriaServico.categoriaServico.id = :categoriaId
            """)
    Page<FornecedorResponseProjection> findFornecedorDetailsByCategoria(Integer categoriaId, Pageable pageable);

    @Query("""
            SELECT ifs.fornecedor.nome as nome,
            ifs.fornecedor.id as id,
            ifs.visaoGeral as visaoGeral,
            ifs.local as local,
            (SELECT AVG(a.nota) FROM Avaliacao a WHERE a.fornecedor.id = :id GROUP BY a.nota) as media,
            (SELECT COUNT(a) FROM Avaliacao a WHERE a.fornecedor.id = :id GROUP BY a) as totalAvaliacoes,
            ifs.urlSite as siteUrl,
            ifs.servicosOferecidos as servicosFornecidos,
            ifs.formaDeTrabalho as formaDeTrabalho,
            ifs.tamanhoCasamento as qtdConvidados
            FROM InformacaoAssociado ifs WHERE ifs.fornecedor.id = :id
            """)
    FornecedorGeralResponseProjection findFornecedorInformations(Integer id);

    Optional<Fornecedor> findByEmail(String email);
}
