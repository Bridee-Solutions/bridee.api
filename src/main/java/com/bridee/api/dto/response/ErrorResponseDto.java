package com.bridee.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Error response",
        description = "Retorna os erros de forma customizada")
public class ErrorResponseDto {

    @Schema(description = "Status code do erro", example = "200")
    private Integer statusCode;
    @Schema(description = "Mensagem do erro")
    private String message;
    @Schema(description = "Descrição do erro")
    private String description;


}
