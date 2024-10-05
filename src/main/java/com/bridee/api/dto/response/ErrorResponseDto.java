package com.bridee.api.dto.response;

import lombok.Data;

@Data
public class ErrorResponseDto {

    private Integer statusCode;
    private String message;
    private String description;


}
