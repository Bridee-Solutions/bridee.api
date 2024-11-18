package com.bridee.api.repository;

import com.bridee.api.entity.SubcategoriaServico;
import com.bridee.api.projection.orcamento.SubcategoriaProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubcategoriaServicoRepository extends JpaRepository<SubcategoriaServico, Integer> {

    @Query("""
            SELECT s FROM SubcategoriaServico s WHERE s.categoriaServico.id = :categoriaId
            """)
    Page<SubcategoriaServico> findAllByCategoriaId(Integer categoriaId, Pageable pageable);

    @Query("""
            SELECT s FROM SubcategoriaServico s WHERE s.categoriaServico.id = :categoriaId
            """)
    List<SubcategoriaServico> findAllByCategoriaId(Integer categoriaId);

    @Query("""
            SELECT s FROM SubcategoriaServico s
            """)
    List<SubcategoriaProjection> findAllProjections();
}
