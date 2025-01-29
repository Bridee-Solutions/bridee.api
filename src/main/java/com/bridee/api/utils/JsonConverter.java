package com.bridee.api.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class JsonConverter {

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    public <T> T convertObject(Object object, Class<T> clazz){
        try{
            return objectMapper().convertValue(object, clazz);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T fromJson(String json, Class<T> clazz){
        try {
            return objectMapper().readValue(json,clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String toJson(Object object){
        try {
            return objectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
