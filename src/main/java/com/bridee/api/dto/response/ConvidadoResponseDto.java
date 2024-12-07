package com.bridee.api.dto.response;

import com.bridee.api.entity.Convidado;
import com.bridee.api.entity.enums.TipoConvidado;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "DTO de resposta do convidado",
        description = "DTO para devolver as informações do convidado")
public class ConvidadoResponseDto {

    private Integer id;
    @Schema(description = "Nome do convidado", example = "Raquel")
    private String nome;
    private String sobrenome;
    @Schema(description = "Telefone do convidado", example = "5579855364120")
    private String telefone;
    @Schema(description = "Status de confirmação de presença", example = "CONFIRMADO")
    private String status;
    @Schema(description = "Faixa Etária do convidado", example = "CRIANÇA")
    private String faixaEtaria;
    @Schema(description = "Se o convidado é titular ou não", example = "NAO_TITULAR")
    private String tipo;
    private Integer mesaId;
    private CategoriaConvidadoResponseDto categoriaConvidado;

    public ConvidadoResponseDto(Convidado convidado){
        this.id = convidado.getId();
        this.nome = convidado.getNome();
        this.sobrenome = convidado.getSobrenome();
        this.telefone = convidado.getTelefone();
        this.status = convidado.getStatus();
        this.tipo = Objects.nonNull(convidado.getTipo()) ? convidado.getTipo().name() : TipoConvidado.NAO_TITULAR.name();
        this.faixaEtaria = convidado.getFaixaEtaria();
        this.mesaId = Objects.nonNull(convidado.getMesa()) ? convidado.getMesa().getId() : null;
        this.categoriaConvidado = new CategoriaConvidadoResponseDto(convidado.getCategoriaConvidado());
    }

}
