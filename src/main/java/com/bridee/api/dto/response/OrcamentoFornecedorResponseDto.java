package com.bridee.api.dto.response;

import com.bridee.api.entity.OrcamentoFornecedor;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrcamentoFornecedorResponseDto {

    private Integer id;
    private BigDecimal preco;
    private FornecedorResponseDto fornecedor;

    public OrcamentoFornecedorResponseDto(OrcamentoFornecedor orcamentoFornecedor){
        this.id = orcamentoFornecedor.getId();
        this.preco = orcamentoFornecedor.getPreco();
        this.fornecedor = new FornecedorResponseDto(orcamentoFornecedor.getFornecedor());
    }

}