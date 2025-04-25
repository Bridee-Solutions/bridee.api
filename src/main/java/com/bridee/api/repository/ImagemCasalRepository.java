package com.bridee.api.repository;

import com.bridee.api.entity.Imagem;
import com.bridee.api.entity.ImagemCasal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ImagemCasalRepository extends JpaRepository<ImagemCasal, Integer> {

    @Query("""
            SELECT ic
            FROM ImagemCasal ic
            WHERE ic.casal.id = :casalId
            AND ic.imagem.tipo = 'Perfil'
            """)
    Optional<ImagemCasal> findProfileCasalImageByCasalId(Integer casalId);

    @Query("""
            SELECT ic.imagem
            FROM ImagemCasal ic
            WHERE ic.casal.id = :casalId
            AND ic.imagem.tipo = 'Perfil'
            """)
    Imagem findProfileImageByCasalId(Integer casalId);

    @Query("""
            SELECT ic.imagem
            FROM ImagemCasal ic
            WHERE ic.casal.id = :casalId
            AND ic.imagem.tipo = 'Favorito'
            """)
    Page<Imagem> findAllCasalFavoritesImages(Pageable pageable, Integer casalId);

    @Query("""
            SELECT COUNT(ic) > 0
            FROM ImagemCasal ic
            WHERE ic.imagem.url = :url
            """)
    boolean existsByUrl(String url);
}
