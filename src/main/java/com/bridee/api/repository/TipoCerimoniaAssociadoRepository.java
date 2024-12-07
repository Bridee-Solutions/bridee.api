package com.bridee.api.repository;

import com.bridee.api.entity.TipoCerimoniaAssociado;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TipoCerimoniaAssociadoRepository extends JpaRepository<TipoCerimoniaAssociado, Integer> {
        
        List<TipoCerimoniaAssociado> findAllByInformacaoAssociadoId(Integer informacaoAssociadoId);
        
        @Transactional
        void deleteById(Integer id);
}