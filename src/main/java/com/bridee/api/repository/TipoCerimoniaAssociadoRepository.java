package com.bridee.api.repository;

import com.bridee.api.entity.TipoCerimonia;
import com.bridee.api.entity.TipoCerimoniaAssociado;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TipoCerimoniaAssociadoRepository extends JpaRepository<TipoCerimoniaAssociado, Integer> {

        @Query("""
                SELECT tca.tipoCerimonia FROM TipoCerimoniaAssociado tca WHERE tca.informacaoAssociado.id = :informacaoAssociadoId
                """)
        List<TipoCerimonia> findAllTipoCerimoniaByInformacaoAssociadoId(Integer informacaoAssociadoId);
        
        @Transactional
        void deleteById(Integer id);
}