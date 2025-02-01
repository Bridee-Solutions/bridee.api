package com.bridee.api.repository;

import java.util.List;

import com.bridee.api.entity.FormaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bridee.api.entity.FormaPagamentoAssociado;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;

public interface FormaPagamentoAssociadoRepository extends JpaRepository<FormaPagamentoAssociado, Integer> {

    @Query("""
            SELECT fp.formaPagamento FROM FormaPagamentoAssociado fp WHERE fp.informacaoAssociado.id = :informacaoAssociadoId
            """)
    List<FormaPagamento> findAllFormaPagamentoByInformacaoAssociadoId(Integer informacaoAssociadoId);

    @Transactional
    void deleteById(Integer id);
}
