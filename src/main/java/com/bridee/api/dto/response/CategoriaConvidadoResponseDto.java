package com.bridee.api.dto.response;

import com.bridee.api.entity.CategoriaConvidado;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaConvidadoResponseDto {

    private Integer id;
    private String nome;

    public CategoriaConvidadoResponseDto(CategoriaConvidado categoriaConvidado) {
        this.nome = categoriaConvidado.getNome().name();
        this.id = categoriaConvidado.getId();
    }
}
