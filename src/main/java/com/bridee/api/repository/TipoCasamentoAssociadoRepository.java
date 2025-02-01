package com.bridee.api.repository;

import java.util.List;

import com.bridee.api.entity.TipoCasamento;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bridee.api.entity.TipoCasamentoAssociado;

import org.springframework.data.jpa.repository.Query;

public interface TipoCasamentoAssociadoRepository extends JpaRepository<TipoCasamentoAssociado, Integer> {

    @Query("""
            SELECT tca.tipoCasamento FROM TipoCasamentoAssociado tca WHERE tca.informacaoAssociado.id = :informacaoAssociadoId
            """)
    List<TipoCasamento> findAllTipoCasamentoByInformacaoAssociadoId(Integer informacaoAssociadoId);
}
