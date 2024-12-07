package com.bridee.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaConvidadoResumoDto {

    private String nome;
    private Integer total;

}
