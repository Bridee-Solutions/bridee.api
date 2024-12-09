package com.bridee.api.dto.request.externo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "DTO de casal externo",
        description = "DTO para enviar informações de casal externo")
public class CasalExternoRequestDto extends UsuarioExternoRequestDto {

    @Size(min = 3)
    @Schema(description = "Nome do parceiro", example = "America")
    private String nomeParceiro;
    @Size(min = 11, max = 11)
    @Schema(description = "Telefone do parceiro", example = "5511987634982")
    private String telefoneParceiro;
    private Integer quantidadeConvidados;
    private String tamanhoCasamento;
    private LocalDate dataCasamento;
    private String endereco;
    private boolean isLocalReservado;
    private String local;
    @Size(min = 8, max = 8)
    private String cep;

}
