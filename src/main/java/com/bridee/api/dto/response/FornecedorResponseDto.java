package com.bridee.api.dto.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class FornecedorResponseDto {

    private Integer id;
    private String nome;
    private String email;
    private Integer nota;

}
