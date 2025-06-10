package com.bridee.api.repository;

import com.bridee.api.entity.Convidado;
import com.bridee.api.entity.enums.TipoConvidado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ConvidadoRepository extends JpaRepository<Convidado, Integer>, JpaSpecificationExecutor<Convidado> {
    boolean existsByTelefoneAndConviteId(String telefone, Integer conviteId);

    @Query("""
            SELECT c FROM Convidado c WHERE c.convite.id = :conviteId
            """)
    List<Convidado> findAllByConviteId(Integer conviteId);
}


