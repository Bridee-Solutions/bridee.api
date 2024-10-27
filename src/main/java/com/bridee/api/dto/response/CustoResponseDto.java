package com.bridee.api.dto.response;

import com.bridee.api.entity.Custo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(name = "DTO de custo",
        description = "DTO para devolver as informações de custo")
public class CustoResponseDto {

    private Integer id;
    @Schema(description = "Nome do custo", example = "Anel")
    private String nome;
    @Schema(description = "Preco do custo", example = "3000.0")
    private BigDecimal precoAtual;

}
