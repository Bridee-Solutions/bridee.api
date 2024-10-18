package com.bridee.api.repository;

import com.bridee.api.entity.FormaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Integer> {

    @Query("""
            SELECT fpa.formaPagamento FROM FormaPagamentoAssociado fpa WHERE fpa.informacaoAssociado.fornecedor.id = :fornecedorId
            """)
    List<FormaPagamento> findByFornecedorId(Integer fornecedorId);

    @Query("""
            SELECT fpa.formaPagamento FROM FormaPagamentoAssociado fpa WHERE fpa.informacaoAssociado.assessor.id = :assessorId
            """)
    List<FormaPagamento> findByAssessorId(Integer assessorId);
}
