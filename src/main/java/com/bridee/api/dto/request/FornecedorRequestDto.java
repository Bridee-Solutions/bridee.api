package com.bridee.api.dto.request;

import lombok.Data;

@Data
public class FornecedorRequestDto {

    private Integer id;
    private String nome;
    private String cnpj;
    private String email;
    private Integer nota;

}
