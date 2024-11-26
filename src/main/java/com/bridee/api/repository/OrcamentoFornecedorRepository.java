package com.bridee.api.repository;

import com.bridee.api.entity.OrcamentoFornecedor;
import com.bridee.api.projection.orcamento.fornecedor.FornecedorProjection;
import com.bridee.api.projection.orcamento.fornecedor.FornecedorBaseProjection;
import com.bridee.api.projection.orcamento.orcamentofornecedor.OrcamentoFornecedorBaseProjection;
import com.bridee.api.projection.orcamento.orcamentofornecedor.OrcamentoFornecedorProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface OrcamentoFornecedorRepository extends JpaRepository<OrcamentoFornecedor, Integer> {

    @Query("""
            SELECT of FROM OrcamentoFornecedor of WHERE of.casal.id = :casalId
            """)
    List<OrcamentoFornecedorProjection> findAllByCasalId(Integer casalId);

    @Query("""
            SELECT of FROM OrcamentoFornecedor of WHERE of.casal.id = :casalId
            """)
    List<OrcamentoFornecedorBaseProjection> findAllBaseProjectionByCasalId(Integer casalId);

    @Query("""
            SELECT of.fornecedor FROM OrcamentoFornecedor of WHERE of.casal.id = :casalId
            """)
    List<FornecedorProjection> findAllFornecedoresByCasalId(Integer casalId);

    @Query("""
            SELECT of.fornecedor FROM OrcamentoFornecedor of WHERE of.casal.id = :casalId
            """)
    List<FornecedorBaseProjection> findFornecedoresBaseProjectionByCasalId(Integer casalId);

    @Query("""
            SELECT of FROM OrcamentoFornecedor of WHERE of.fornecedor.subcategoriaServico.id = :subcategoriaId
            AND of.casal.id = :casalId
            """)
    List<OrcamentoFornecedor> findAllByCasalIdAndSubcategoriaId(Integer casalId, Integer subcategoriaId);

    @Query("""
            UPDATE OrcamentoFornecedor of SET of.preco = :preco WHERE of.id = :id
            """)
    @Modifying
    void updateOrcamentoFornecedorPreco(Integer id, BigDecimal preco);

    @Query("""
            SELECT SUM(of.preco) FROM OrcamentoFornecedor of WHERE of.casal.id = :casalId
            """)
    long totalOrcamentoFornecedorPrice(Integer casalId);
}
