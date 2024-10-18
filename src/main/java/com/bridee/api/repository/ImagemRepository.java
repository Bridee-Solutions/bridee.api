package com.bridee.api.repository;

import com.bridee.api.entity.Imagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImagemRepository extends JpaRepository<Imagem, Integer> {

    @Query("""
            SELECT ia.imagem FROM ImagemAssociado ia WHERE ia.informacaoAssociado.fornecedor.id = :fornecedorId
            """)
    List<Imagem> findByFornecedorId(Integer fornecedorId);

    @Query("""
            SELECT ia.imagem FROM ImagemAssociado ia WHERE ia.informacaoAssociado.assessor.id = :assessorId
            """)
    List<Imagem> findByAssessorId(Integer assessorId);
}
