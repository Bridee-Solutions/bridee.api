package com.bridee.api.dto.response;

import com.bridee.api.entity.ItemOrcamento;
import lombok.Data;

import java.util.List;

@Data
public class ItemOrcamentoResponseDto {

    private Integer id;
    private String tipo;
    private List<CustoResponseDto> custos;

}
