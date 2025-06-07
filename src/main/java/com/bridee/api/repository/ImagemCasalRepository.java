package com.bridee.api.repository;

import com.bridee.api.entity.Imagem;
import com.bridee.api.entity.ImagemCasal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
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
            AND ic.casal.id = :casalId
            """)
    boolean existsByUrlAndCasalId(Integer casalId, String url);

    @Query("""
            SELECT ic.imagem
            FROM ImagemCasal ic
            WHERE ic.casal.id = :casalId
            AND ic.imagem.tipo = 'Favorito'
            """)
    List<Imagem> findAllCasalFavoritesImages(Integer casalId);

    @Query("""
            SELECT COUNT(im) > 0
            FROM Imagem im
            WHERE im.url = :url
            """)
    boolean existsImageByUrl(String url);

    @Query("""
            SELECT im
            FROM Imagem im
            WHERE im.url = :url
            """)
    Optional<Imagem> findByUrl(String url);

    @Query("""
            DELETE FROM ImagemCasal im
            WHERE im.imagem.id = :id
            AND im.casal.id = :casalId
            """)
    @Modifying
    void deleteByImagemIdAndCasalId(Integer id, Integer casalId);

    @Query("""
            SELECT COUNT(im) > 0 FROM ImagemCasal im
            WHERE im.imagem.id = :id
            """)
    boolean existsByImagemId(Integer id);
}
