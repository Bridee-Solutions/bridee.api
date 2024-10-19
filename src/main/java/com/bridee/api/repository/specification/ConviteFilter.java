package com.bridee.api.repository.specification;

import com.bridee.api.entity.Convidado;
import com.bridee.api.entity.Convite;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Fetch;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ConviteFilter implements Specification<Convite> {

    @Setter
    private Integer casamentoId;
    private String status;
    private String faixaEtaria;
    private String categoria;

    public ConviteFilter() {
    }

    public ConviteFilter(String status, String faixaEtaria, String categoria) {
        this.status = status;
        this.faixaEtaria = faixaEtaria;
        this.categoria = categoria;
    }

    @Override
    public Predicate toPredicate(Root<Convite> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();
        Fetch<Convidado, Convite> fetchConvidado = root.fetch("convidados");
        Join<Convidado, Convite> convidado = (Join<Convidado, Convite>) fetchConvidado;

        if (casamentoId != null){
            predicates.add(criteriaBuilder.equal(root.get("casamento").get("id"), casamentoId));
        }
        if (StringUtils.isNotBlank(status)){
            predicates.add(criteriaBuilder.equal(convidado.get("status"), status));
        }
        if (StringUtils.isNotBlank(faixaEtaria)){
            predicates.add(criteriaBuilder.equal(convidado.get("faixaEtaria"), faixaEtaria));
        }
        if (StringUtils.isNotBlank(categoria)){
            predicates.add(criteriaBuilder.equal(convidado.get("categoria"), categoria));
        }

        return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }

    public void fillSpecification(Map<String, Object> params){
        params.forEach((key, value) -> fieldNames().forEach(field -> {
            if (field.getName().equals(key)) {
                fillField(field, value);
            }
        }));
    }

    private List<Field> fieldNames(){
        return Arrays.stream(this.getClass().getDeclaredFields()).toList();
    }

    private void fillField(Field field, Object value){
        field.setAccessible(true);
        try {
            field.set(this, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
