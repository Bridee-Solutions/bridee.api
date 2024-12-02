package com.bridee.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridee.api.entity.TipoCasamentoAssociado;

import jakarta.transaction.Transactional;

public interface TipoCasamentoAssociadoRepository extends JpaRepository<TipoCasamentoAssociado, Integer> {
    
    List<TipoCasamentoAssociado> findAllByInformacaoAssociadoId(Integer informacaoAssociadoId);

    @Transactional
    void deleteById(Integer id);
}
