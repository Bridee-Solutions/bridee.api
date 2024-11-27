package com.bridee.api.repository;

import com.bridee.api.entity.Assessor;
import com.bridee.api.entity.Casamento;
import com.bridee.api.entity.CasamentoAssessorado;
import com.bridee.api.entity.enums.CasamentoStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CasamentoAssessoradoRepository extends JpaRepository<CasamentoAssessorado, Integer>, JpaSpecificationExecutor<CasamentoAssessorado> {

    @Query("""
            SELECT ca.casamento FROM CasamentoAssessorado ca WHERE ca.assessor.id = :assessorId AND ca.casamento.status = :casamentoStatusEnum
            """)
    Page<Casamento> findAllCasamentoPendente(Integer assessorId, CasamentoStatusEnum casamentoStatusEnum, Pageable pageable);

    @Query("""
            SELECT ca FROM CasamentoAssessorado ca WHERE ca.casamento.id = :casamentoId
            """)
    CasamentoAssessorado findAssessorByCasamentoId(Integer casamentoId);

    @Query("""
            SELECT ca FROM CasamentoAssessorado ca WHERE ca.casamento.id = :casamentoId
            """)
    Optional<CasamentoAssessorado> findByCasamentoId(Integer casamentoId);

    boolean existsByAssessorIdAndCasamentoId(Integer assessorId, Integer casamentoId);

    @Query("""
            UPDATE CasamentoAssessorado ca SET ca.preco = :preco
            WHERE ca.casamento.id = :casamentoId AND ca.assessor.id = :assessorId
            """)
    @Modifying
    int updatePreco(Integer assessorId, Integer casamentoId, BigDecimal preco);

    Optional<CasamentoAssessorado> findByCasamentoIdAndAssessorId(Integer casamentoId, Integer assessorId);
}
