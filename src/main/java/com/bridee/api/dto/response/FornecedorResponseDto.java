package com.bridee.api.dto.response;

import com.bridee.api.entity.Fornecedor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class FornecedorResponseDto {

    private Integer id;
    private String nome;
    private String email;
    private Integer nota;
    private SubcategoriaServicoResponseDto subcategoriaServico;

}
