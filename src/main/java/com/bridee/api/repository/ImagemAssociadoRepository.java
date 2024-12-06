package com.bridee.api.repository;

import com.bridee.api.entity.ImagemAssociado;
import com.bridee.api.entity.enums.TipoImagemAssociadoEnum;
import com.bridee.api.projection.associado.ImagemAssociadoProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImagemAssociadoRepository extends JpaRepository<ImagemAssociado, Integer> {

    @Query("""
            SELECT ima.imagem.id as id, ima.imagem.nome as nome FROM ImagemAssociado ima WHERE ima.informacaoAssociado.id = :informacaoId AND ima.tipo = :tipo
            """)
    ImagemAssociadoProjection findImagemAssociadoByTipo(Integer informacaoId, TipoImagemAssociadoEnum tipo);

    @Query("""
            SELECT ima.imagem.nome FROM ImagemAssociado ima WHERE ima.informacaoAssociado.id = :informacaoId AND ima.tipo = :tipo
            """)
    List<String> findImagensAssociadoByTipo(Integer informacaoId, TipoImagemAssociadoEnum tipo);
}
