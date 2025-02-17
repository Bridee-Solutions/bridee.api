package com.bridee.api.repository;

import com.bridee.api.entity.TipoCasamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TipoCasamentoRepository extends JpaRepository<TipoCasamento, Integer> {

    @Query("""
            SELECT tc.tipoCasamento.nome FROM TipoCasamentoAssociado tc WHERE tc.informacaoAssociado.fornecedor.id = :id
            """)
    List<String> findByInformacaoFornecedorId(Integer id);

    @Query("""
            SELECT tc.tipoCasamento.nome FROM TipoCasamentoAssociado tc WHERE tc.informacaoAssociado.assessor.id = :id
            """)
    List<String> findByInformacaoAssessorId(Integer id);
}
