package com.bridee.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "Categoria Serviço response DTO",
        description = "DTO para devolver as informações de uma categoria")
public class CategoriaServicoResponseDto {

    private Integer id;
    @Schema(description = "Nome da categoria", example = "Joias")
    private String nome;
    @Schema(description = "Url da imagem", example = "http://imagem")
    private String imagemUrl;

    private List<SubcategoriaServicoResponseDto> subcategorias;

}
