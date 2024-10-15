package com.bridee.api.dto.response;

import com.bridee.api.entity.Convidado;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConvidadoResponseDto {

    private Integer id;
    private String nome;
    private String categoria;
    private String telefone;
    private String status;
    private String tipo;

    public ConvidadoResponseDto(Convidado convidado){
        this.id = convidado.getId();
        this.nome = convidado.getNome();
        this.categoria = convidado.getCategoria();
        this.telefone = convidado.getTelefone();
        this.status = convidado.getStatus();
        this.tipo = convidado.getTipo();
    }

}
