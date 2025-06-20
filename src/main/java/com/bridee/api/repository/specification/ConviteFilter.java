package com.bridee.api.repository.specification;

import com.bridee.api.entity.CategoriaConvidado;
import com.bridee.api.entity.Convidado;
import com.bridee.api.entity.Convite;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Fetch;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
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
    private String convidado;
    private String convite;

    public ConviteFilter() {
    }

    @Override
    public Predicate toPredicate(Root<Convite> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();
        Join<Convite, Convidado> convidado = null;
        if (query.getResultType() == Long.class){
            convidado = root.join("convidados");
        }else {
            Fetch<Convite, Convidado> fetchConvidado = root.fetch("convidados");
            convidado = (Join<Convite, Convidado>) fetchConvidado;
        }

        if (casamentoId != null){
            predicates.add(criteriaBuilder.equal(root.get("casamento").get("id"), casamentoId));
        }
        if (StringUtils.isNotBlank(status)){
            predicates.add(criteriaBuilder.equal(convidado.get("status"), status.trim()));
        }
        if (StringUtils.isNotBlank(faixaEtaria)){
            predicates.add(criteriaBuilder.equal(convidado.get("faixaEtaria"), faixaEtaria.trim()));
        }
        if (StringUtils.isNotBlank(categoria)){
            Join<Convidado, CategoriaConvidado> categoriaConvidado = convidado.join("categoriaConvidado");
            predicates.add(criteriaBuilder.equal(categoriaConvidado.get("nome"), categoria.trim()));
        }
        if (StringUtils.isNotBlank(convite)){
            predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("nome")), "%"+convite.toUpperCase().trim()+"%"));
        }
        if (StringUtils.isNotBlank(this.convidado)){
            predicates.add(criteriaBuilder.like(criteriaBuilder.upper(convidado.get("nome")), "%"+this.convidado.toUpperCase().trim()+"%"));
        }

        return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }

    public void fillSpecification(Map<String, Object> params){
        List<Field> fieldNames = fieldNames();
        params.forEach((key, value) -> fieldNames.forEach(field -> {
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
        }finally {
            field.setAccessible(false);
        }
    }
}
