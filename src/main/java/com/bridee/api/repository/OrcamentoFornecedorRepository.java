package com.bridee.api.repository;

import com.bridee.api.entity.OrcamentoFornecedor;
import com.bridee.api.projection.orcamento.FornecedorProjection;
import com.bridee.api.projection.orcamento.OrcamentoFornecedorProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrcamentoFornecedorRepository extends JpaRepository<OrcamentoFornecedor, Integer> {

    @Query("""
            SELECT of FROM OrcamentoFornecedor of WHERE of.casal.id = :casalId
            """)
    List<OrcamentoFornecedorProjection> findAllByCasalId(Integer casalId);

    @Query("""
            SELECT of.fornecedor FROM OrcamentoFornecedor of WHERE of.casal.id = :casalId
            """)
    List<FornecedorProjection> findAllFornecedoresByCasalId(Integer casalId);

}
