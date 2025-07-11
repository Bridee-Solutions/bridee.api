package com.bridee.api.repository;

import com.bridee.api.entity.SubcategoriaServico;
import com.bridee.api.repository.projection.orcamento.SubcategoriaProjection;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import java.util.List;

public interface SubcategoriaServicoRepository extends JpaRepository<SubcategoriaServico, Integer> {

    @Query("""
            SELECT f.subcategoriaServico FROM Fornecedor f WHERE f.id = :id
            """)
    @QueryHints({
            @QueryHint(name = "org.hibernate.readOnly", value="true")
    })
    SubcategoriaServico findByFornecedorId(Integer id);

    @Query("""
            SELECT s FROM SubcategoriaServico s WHERE UPPER(s.categoriaServico.nome) like UPPER(concat('%',:nome,'%'))
            """)
    Page<SubcategoriaServico> findAllByCategoriaNome(String nome, Pageable pageable);

    @Query("""
            SELECT s FROM SubcategoriaServico s JOIN FETCH s.categoriaServico
            """)
    List<SubcategoriaServico> findAllSubcategories();
}
