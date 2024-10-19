package com.bridee.api.dto.response;

import com.bridee.api.entity.Custo;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CustoResponseDto {

    private Integer id;
    private String nome;
    private BigDecimal precoAtual;

}
