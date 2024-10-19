package com.bridee.api.dto.response;

import com.bridee.api.entity.SubcategoriaServico;
import lombok.Data;

@Data
public class SubcategoriaServicoResponseDto {

    private Integer id;
    private String nome;
    private String imagemUrl;

}
