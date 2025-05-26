package com.bridee.api.repository.specification;

import com.bridee.api.entity.Fornecedor;
import com.bridee.api.entity.SubcategoriaServico;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
public class FornecedorSpecification implements Specification<Fornecedor>{

    private String nome;
    private Integer subcategoriaId;

    @Override
    public Predicate toPredicate(Root<Fornecedor> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        Join<Fornecedor, SubcategoriaServico> subcategoriaServico = root.join("subcategoriaServico");
        if(StringUtils.isNotBlank(nome)){
            predicates.add(criteriaBuilder
                    .like(criteriaBuilder.upper(root.get("nome")), "%" + nome.toUpperCase() + "%"));
        }
        if(Objects.nonNull(subcategoriaId)){
            predicates.add(criteriaBuilder
                    .equal(subcategoriaServico.get("id"), subcategoriaId));
        }
        return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }
}
