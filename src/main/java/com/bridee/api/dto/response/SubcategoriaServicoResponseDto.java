package com.bridee.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "DTO de subcategoria",
        description = "DTO para devolver os valores de subcategoria")
public class SubcategoriaServicoResponseDto {

    private Integer id;
    private String nome;
    private String imagemUrl;

}
