package com.bridee.api.repository;

import com.bridee.api.entity.Casamento;
import com.bridee.api.entity.PedidoAssessoria;
import com.bridee.api.entity.enums.PedidoAssessoriaStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PedidoAssessoriaRepository extends JpaRepository<PedidoAssessoria, Integer>, JpaSpecificationExecutor<PedidoAssessoria> {

    @Query("""
            SELECT pa.casamento FROM PedidoAssessoria pa WHERE pa.assessor.id = :assessorId AND pa.status = :status
            """)
    Page<Casamento> findAllCasamentoPendente(Integer assessorId, PedidoAssessoriaStatusEnum status, Pageable pageable);

    @Query("""
            SELECT pa FROM PedidoAssessoria pa WHERE pa.casamento.id = :casamentoId
            """)
    PedidoAssessoria findAssessorByCasamentoId(Integer casamentoId);

    @Query("""
            SELECT pa FROM PedidoAssessoria pa WHERE pa.casamento.id = :casamentoId
            """)
    List<PedidoAssessoria> findByCasamentoId(Integer casamentoId);

    boolean existsByAssessorIdAndCasamentoIdAndStatus(Integer assessorId, Integer casamentoId, PedidoAssessoriaStatusEnum status);

    @Query("""
            UPDATE PedidoAssessoria pa SET pa.preco = :preco
            WHERE pa.casamento.id = :casamentoId AND pa.assessor.id = :assessorId
            """)
    @Modifying
    int updatePreco(Integer assessorId, Integer casamentoId, BigDecimal preco);

    Optional<PedidoAssessoria> findByCasamentoIdAndAssessorId(Integer casamentoId, Integer assessorId);
}
