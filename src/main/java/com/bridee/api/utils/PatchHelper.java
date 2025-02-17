package com.bridee.api.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.stereotype.Component;

import javax.json.JsonMergePatch;
import javax.json.JsonValue;
import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Set;

@Component
public class PatchHelper {

    private final ObjectMapper objectMapper;
    private final Validator validator;

    public PatchHelper(ObjectMapper objectMapper, Validator validator) {
        this.validator = validator;
        this.objectMapper = objectMapper;
    }

    public void mergeNonNull(Object source, Object destination){
        Field[] sourceFields = allFields(source);
        Field[] destinationFields = allFields(destination);

        for (Field sourceField: sourceFields){
            for(Field destinationField: destinationFields){
                if(sourceField.getName().equals(destinationField.getName())){
                    try {
                        sourceField.setAccessible(true);
                        Object sourceFieldValue = sourceField.get(source);
                        if(Objects.nonNull(sourceFieldValue)){
                            destinationField.setAccessible(true);
                            destinationField.set(destination, sourceFieldValue);
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }finally {
                        sourceField.setAccessible(false);
                        destinationField.setAccessible(false);
                    }
                };
            }
        }
    }

    private Field[] allFields(Object object){
        return fillObjectFields(object);
    }

    private Field[] fillObjectFields(Object object){
        Class<?> objectClass = object.getClass();
        Field[] objectFields = objectClass.getDeclaredFields();
        if (!isSuperClassObject(objectClass)){
            return extractAllInheritanceFields(objectClass, objectFields);
        }
        return objectFields;
    }

    private boolean isSuperClassObject(Class<?> objectClass){
        return objectClass.getSuperclass().isAssignableFrom(Object.class);
    }

    private Field[] extractAllInheritanceFields(Class<?> objectClass, Field[] objectFields){
        Field[] objectSuperClassFields = objectClass.getSuperclass().getDeclaredFields();
        Field[] allFields = new Field[objectSuperClassFields.length + objectFields.length];
        for (int i = 0; i < objectFields.length; i++) {
            allFields[i] = objectFields[i];
        }
        for (int i = objectFields.length; i < allFields.length; i++) {
            allFields[i] = objectSuperClassFields[i - objectFields.length];
        }
        return allFields;
    }

    public <T> T mergePatch(JsonMergePatch jsonMergePatch, T targetBean, Class<? extends T > beanClass){
        JsonValue target = objectMapper.convertValue(targetBean, JsonValue.class);
        JsonValue patched = jsonMergePatch.apply(target);
        return convertAndValidate(patched, beanClass);
    }

    private <T> T convertAndValidate(JsonValue patched, Class<T> beanClass) {
        T bean = objectMapper.convertValue(patched, beanClass);
        validate(bean);
        return bean;
    }

    private <T> void validate(T bean) {
        Set<ConstraintViolation<T>> violations = validator.validate(bean);
        if (!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
    }
}
