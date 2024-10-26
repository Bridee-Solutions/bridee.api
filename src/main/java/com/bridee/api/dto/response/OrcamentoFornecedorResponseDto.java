package com.bridee.api.dto.response;

import com.bridee.api.entity.OrcamentoFornecedor;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(name = "Orcamento fornecedor DTO",
        description = "DTO para retornar o orçamento do fornecedor")
public class OrcamentoFornecedorResponseDto {

    private Integer id;
    @Schema(description = "Valor")
    private BigDecimal preco;
    @Schema(description = "Fornecedor")
    private FornecedorResponseDto fornecedor;

}
