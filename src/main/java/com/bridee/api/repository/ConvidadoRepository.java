package com.bridee.api.repository;

import com.bridee.api.entity.Convidado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConvidadoRepository extends JpaRepository<Convidado, Integer>, JpaSpecificationExecutor<Convidado> {
    boolean existsByTelefoneAndConviteId(String telefone, Integer conviteId);

//    @Query("""
//            SELECT c FROM Convidado c WHERE c.convite.casamento.id = :casamentoId
//            """)
//    List<Convidado> findAllByCasamentoId(Integer casamentoId);
}


