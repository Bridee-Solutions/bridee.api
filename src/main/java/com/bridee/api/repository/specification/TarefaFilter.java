package com.bridee.api.repository.specification;

import com.bridee.api.entity.Tarefa;
import com.bridee.api.entity.TarefaCasal;
import com.bridee.api.entity.enums.TarefaCategoriaEnum;
import com.bridee.api.entity.enums.TarefaStatusEnum;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Fetch;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.flywaydb.core.internal.util.CollectionsUtils;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TarefaFilter implements Specification<Tarefa> {

    private List<TarefaCategoriaEnum> categoria;
    private String nome;
    private TarefaStatusEnum status;
    private List<Integer> mes;
    private Integer casalId;

    @Override
    public Predicate toPredicate(Root<Tarefa> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> andPrecidates = new ArrayList<>();
        List<Predicate> orPredicates = new ArrayList<>();
        Join<Tarefa, TarefaCasal> tarefaCasal = null;
        if (query.getResultType() == Long.class){
            tarefaCasal = root.join("tarefaCasals");
        }else{
            Fetch<Tarefa, TarefaCasal> tarefaCasalFetch = root.fetch("tarefaCasals");
            tarefaCasal = (Join<Tarefa, TarefaCasal>) tarefaCasalFetch;
        }

        if (Objects.nonNull(categoria) && CollectionsUtils.hasItems(categoria)){
            categoria.forEach(cat -> {
                orPredicates.add(criteriaBuilder.like(criteriaBuilder
                        .upper(root.get("categoria")), "%" + cat + "%"));
            });
        }

        if (Objects.nonNull(status) && StringUtils.isNotBlank(status.getValue())){
            andPrecidates.add(criteriaBuilder.like(criteriaBuilder
                    .upper(root.get("status")), "%" + status + "%"));
        }

        if (StringUtils.isNotBlank(nome)){
            andPrecidates.add(criteriaBuilder.like(criteriaBuilder
                    .upper(root.get("nome")), "%" + nome.toUpperCase() + "%"));
        }

        if (Objects.nonNull(mes)){
            filterDate().forEach((date) -> {
                orPredicates.add(criteriaBuilder.between(root.get("dataLimite"), date.getDataInicio(), date.getDataFim()));
            } );
        }

        if (Objects.nonNull(casalId)){
            andPrecidates.add(criteriaBuilder.equal(tarefaCasal.get("casal").get("id"), casalId));
        }

        Predicate andPredicate = criteriaBuilder.and(andPrecidates.toArray(Predicate[]::new));
        Predicate orPredicate = criteriaBuilder.or(orPredicates.toArray(Predicate[]::new));

        return criteriaBuilder.and(andPredicate, orPredicate);
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

        Class<?> rawClass = extractFieldRawClass(field);
        if (rawClass.isAssignableFrom(List.class)){
            setFieldListAttribute(field, value);
        }else{
            setFieldSimpleAttribute(field, value);
        }

    }

    private void setFieldSimpleAttribute(Field field, Object value) throws IllegalAccessException {
        if (((Class<?>) field.getGenericType()).isEnum()){
            field.set(this, generateEnumValue(field.getType(), String.valueOf(value)));
            return;
        }
        if (field.getType() == Integer.class){
            field.set(this, Integer.parseInt(String.valueOf(value)));
            return;
        }
        field.set(this, value);
    }

    private void setFieldListAttribute(Field field, Object value) throws IllegalAccessException {

        JavaType javaType = extractJavaType(field);

        List<String> values = Arrays.stream(String.valueOf(value).split(",")).toList();
        if (javaType.getContentType().getRawClass() == Integer.class){
            List<Integer> integerList = values.stream().map(Integer::parseInt).toList();
            field.set(this, integerList);
            return;
        }

        if ((javaType.getContentType().getRawClass()).isEnum()){
            List<Enum> enumList = values.stream().map(enumerator -> {
                Class<?> enumClass = extractFieldListContentType(field);
                return generateEnumValue(enumClass, enumerator);
            }).toList();
            field.set(this, enumList);
        }
    }

    private Enum generateEnumValue(Class<?> enumClass, String enumerator){
        return Enum.valueOf((Class<Enum>) enumClass, enumerator);
    }

    private Field[] extractField(){
        return this.getClass().getDeclaredFields();
    }

    private Class<?> extractFieldRawClass(Field field){
        JavaType javaType = extractJavaType(field);
        return javaType.getRawClass();
    }

    private Class<?> extractFieldListContentType(Field field){
        JavaType javaType = extractJavaType(field);
        return javaType.getContentType().getRawClass();
    }

    private JavaType extractJavaType(Field field){
        Type rawType = field.getGenericType();
        return TypeFactory.defaultInstance().constructType(rawType);
    };

    private List<DataFilterDto> filterDate(){
        return mes.stream().map((mes) -> {
            LocalDate localDate = LocalDate.of(LocalDate.now().getYear(), mes, LocalDate.now().getDayOfMonth());
            LocalDate dataInicioMesAtual = LocalDate.of(LocalDate.now().getYear(), mes,
                    localDate.with(TemporalAdjusters.firstDayOfMonth()).getDayOfMonth());
            LocalDate dataFimMesAtual = LocalDate.of(LocalDate.now().getYear(), mes,
                    localDate.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth());
            return new DataFilterDto(dataInicioMesAtual, dataFimMesAtual);
        }).toList();
    }
}
