package com.bridee.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "DTO de fornecedor",
        description = "DTO para devolver as informações de fornecedor")
public class FornecedorResponseDto {

    private Integer id;
    @Schema(description = "Nome do fornecedor", example = "João")
    private String nome;
    @Schema(description = "Email do fornecedor", example = "joao@example.com")
    private String email;
    private Integer nota;
    private SubcategoriaServicoResponseDto subcategoriaServico;

}
