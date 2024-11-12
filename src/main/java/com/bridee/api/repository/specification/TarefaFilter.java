package com.bridee.api.repository.specification;

import com.bridee.api.entity.Tarefa;
import com.bridee.api.entity.TarefaCasal;
import com.bridee.api.entity.enums.TarefaCategoriaEnum;
import com.bridee.api.entity.enums.TarefaStatusEnum;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Fetch;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TarefaFilter implements Specification<Tarefa> {

    private TarefaCategoriaEnum categoria;
    private String nome;
    private TarefaStatusEnum status;
    private Integer mes;
    private Integer casalId;

    @Override
    public Predicate toPredicate(Root<Tarefa> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        Join<Tarefa, TarefaCasal> tarefaCasal = null;
        if (query.getResultType() == Long.class){
            tarefaCasal = root.join("tarefaCasals");
        }else{
            Fetch<Tarefa, TarefaCasal> tarefaCasalFetch = root.fetch("tarefaCasals");
            tarefaCasal = (Join<Tarefa, TarefaCasal>) tarefaCasalFetch;
        }

        if (Objects.nonNull(categoria) && StringUtils.isNotBlank(categoria.getValue())){
            predicates.add(criteriaBuilder.like(criteriaBuilder
                    .upper(root.get("categoria")), "%" + categoria + "%"));
        }

        if (Objects.nonNull(status) && StringUtils.isNotBlank(status.getValue())){
            predicates.add(criteriaBuilder.like(criteriaBuilder
                    .upper(root.get("status")), "%" + status + "%"));
        }

        if (StringUtils.isNotBlank(nome)){
            predicates.add(criteriaBuilder.like(criteriaBuilder
                    .upper(root.get("nome")), "%" + nome.toUpperCase() + "%"));
        }

        if (Objects.nonNull(mes)){
            LocalDate localDate = LocalDate.of(LocalDate.now().getYear(), mes, LocalDate.now().getDayOfMonth());
            LocalDate dataInicioMesAtual = LocalDate.of(LocalDate.now().getYear(), mes,
                    localDate.with(TemporalAdjusters.firstDayOfMonth()).getDayOfMonth());
            LocalDate dataFimMesAtual = LocalDate.of(LocalDate.now().getYear(), mes,
                    localDate.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth());
            predicates.add(criteriaBuilder.between(root.get("dataLimite"), dataInicioMesAtual, dataFimMesAtual));
        }

        if (Objects.nonNull(casalId)){
            predicates.add(criteriaBuilder.equal(tarefaCasal.get("casal").get("id"), casalId));
        }

        return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }

    public void buildFilter(Map<String, Object> params){
        Field[] fields = extractField();
        params.forEach((key, value) -> {
            for (Field field: fields){
                if (key.equals(field.getName())){
                    associateParamsValues(key, value, field);
                }
            }
        });
    }

    private void associateParamsValues(String key, Object value, Field field){
        if (key.equals(field.getName())){
            field.setAccessible(true);
            try {
                setFieldAttribute(field, value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void setFieldAttribute(Field field, Object value) throws IllegalAccessException {
        if (((Class<?>) field.getGenericType()).isEnum()){
            field.set(this, Enum.valueOf((Class<Enum>) field.getType(), String.valueOf(value)));
            return;
        }
        if (field.getType() == Integer.class){
            field.set(this, Integer.parseInt(String.valueOf(value)));
            return;
        }
        field.set(this, value);
    }

    private Field[] extractField(){
        return this.getClass().getDeclaredFields();
    }
}
