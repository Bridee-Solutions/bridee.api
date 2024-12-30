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
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public interface PedidoAssessoriaRepository extends JpaRepository<PedidoAssessoria, Integer>, JpaSpecificationExecutor<PedidoAssessoria> {

    @Query("""
            SELECT pa.casamento FROM PedidoAssessoria pa WHERE pa.assessor.id = :assessorId AND pa.status = :status
            """)
    Page<Casamento> findAllCasamentoPendente(Integer assessorId, PedidoAssessoriaStatusEnum status, Pageable pageable);

    @Query("""
            SELECT pa FROM PedidoAssessoria pa WHERE pa.casamento.id = :casamentoId AND pa.status = :status
            """)
    List<PedidoAssessoria> findAllPedidosCasamentoByStatus(Integer casamentoId, PedidoAssessoriaStatusEnum status);

    @Query("""
            SELECT pa FROM PedidoAssessoria pa WHERE pa.casamento.id = :casamentoId AND pa.assessor.id = :assessorId AND pa.status = :status
            """)
    Optional<PedidoAssessoria> findPedidoCasamentoByStatus(Integer casamentoId, Integer assessorId,PedidoAssessoriaStatusEnum status);

    @Query("""
            SELECT pa FROM PedidoAssessoria pa WHERE pa.casamento.id = :casamentoId AND pa.status = :status
            """)
    Optional<PedidoAssessoria> findPedidoByStatus(Integer casamentoId, PedidoAssessoriaStatusEnum status);


    @Query("""
            SELECT pa FROM PedidoAssessoria pa WHERE pa.casamento.id = :casamentoId AND pa.assessor.id = :assessorId
            """)
    Optional<PedidoAssessoria> findByCasamentoAndAssessor(Integer casamentoId, Integer assessorId);

    boolean existsByAssessorIdAndCasamentoIdAndStatus(Integer assessorId, Integer casamentoId, PedidoAssessoriaStatusEnum status);

    @Query("""
            UPDATE PedidoAssessoria pa SET pa.preco = :preco
            WHERE pa.casamento.id = :casamentoId AND pa.assessor.id = :assessorId
            """)
    @Modifying
    int updatePreco(Integer assessorId, Integer casamentoId, BigDecimal preco);

    @Query("""
            SELECT p.casamento FROM PedidoAssessoria p WHERE p.casamento.dataFim < NOW()
            """)
    List<PedidoAssessoria> findAllPedidosCasamentoInvalido();
}
