package com.bridee.api.repository;

import com.bridee.api.entity.SubcategoriaServico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SubcategoriaServicoRepository extends JpaRepository<SubcategoriaServico, Integer> {

    @Query("""
            SELECT s FROM SubcategoriaServico s WHERE s.categoriaServico.id = :categoriaId
            """)
    Page<SubcategoriaServico> findAllByCategoriaNome(Integer categoriaId, Pageable pageable);
}
