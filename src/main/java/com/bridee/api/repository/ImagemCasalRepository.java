package com.bridee.api.repository;

import com.bridee.api.entity.Imagem;
import com.bridee.api.entity.ImagemCasal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ImagemCasalRepository extends JpaRepository<ImagemCasal, Integer> {
    Optional<ImagemCasal> findByCasalId(Integer casalId);

    @Query("""
            SELECT ic.imagem FROM ImagemCasal ic WHERE ic.casal.id = :casalId
            """)
    Imagem findImageByCasalId(Integer casalId);
}
