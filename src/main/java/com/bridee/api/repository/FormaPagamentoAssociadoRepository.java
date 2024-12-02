package com.bridee.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridee.api.entity.FormaPagamentoAssociado;

import jakarta.transaction.Transactional;

public interface FormaPagamentoAssociadoRepository extends JpaRepository<FormaPagamentoAssociado, Integer> {
    List<FormaPagamentoAssociado> findAllByInformacaoAssociadoId(Integer informacaoAssociadoId);
    
    @Transactional
    void deleteById(Integer id);
}
