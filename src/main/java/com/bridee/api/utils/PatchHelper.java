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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.json.JsonMergePatch;
import javax.json.JsonValue;
import java.util.Set;

@Component
public class PatchHelper {

    private final ObjectMapper objectMapper;
    private final Validator validator;

    public PatchHelper(ObjectMapper objectMapper, Validator validator) {
        this.validator = validator;
        this.objectMapper =  JsonMapper.builder()
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .findAndAddModules()
                .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
                .build();;
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
