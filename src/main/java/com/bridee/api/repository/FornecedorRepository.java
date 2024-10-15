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

    Optional<Fornecedor> findByCnpj(String cnpj);

    @Query("""
            SELECT f.nome as nome,
            f.id as id,
            f.informacaoAssociado.visaoGeral as visaoGeral,
            f.informacaoAssociado.local as local,
            (SELECT AVG(a.nota) FROM Avaliacao a WHERE a.fornecedor.id = :subcategoriaId GROUP BY a.nota) as media,
            (SELECT COUNT(a) FROM Avaliacao a WHERE a.fornecedor.id = :subcategoriaId GROUP BY a) as totalAvaliacoes
            FROM Fornecedor f WHERE f.subcategoriaServico.id = :subcategoriaId
            """)
    Page<FornecedorResponseProjection> findFornecedorDetailsBySubcategoria(Integer subcategoriaId, Pageable pageable);

    @Query("""
            SELECT f.nome as nome,
            f.id as id,
            f.informacaoAssociado.visaoGeral as visaoGeral,
            f.informacaoAssociado.local as local,
            f.informacaoAssociado.imagemAssociados as imagens,
            f.informacaoAssociado.formaPagamentoAssociados as formaPagamento,
            (SELECT AVG(a.nota) FROM Avaliacao a WHERE a.fornecedor.id = :id GROUP BY a.nota) as media,
            (SELECT COUNT(a) FROM Avaliacao a WHERE a.fornecedor.id = :id GROUP BY a) as totalAvaliacoes
            FROM Fornecedor f WHERE f.id = :id
            """)
    FornecedorGeralResponseProjection findFornecedorInformations(Integer id);
}
