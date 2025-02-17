package com.bridee.api.exception;

import com.bridee.api.dto.response.ErrorResponseDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@EqualsAndHashCode(callSuper = true)
public class ValidationError extends ErrorResponseDto {

    private List<FieldMessage> erros = new ArrayList<>();

    public void addError(String fieldName, String message){
        this.erros.add(new FieldMessage(fieldName, message));
    }
}

