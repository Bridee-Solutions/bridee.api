package com.bridee.api.dto.response;

import com.bridee.api.entity.ItemOrcamento;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "DTO de item orçamento",
        description = "DTO para devolver os dados de item orçamento")
public class ItemOrcamentoResponseDto {

    private Integer id;
    @Schema(description = "tipo do item", example = "Vestido")
    private String tipo;
    @Schema(description = "custos")
    private List<CustoResponseDto> custos;

}
