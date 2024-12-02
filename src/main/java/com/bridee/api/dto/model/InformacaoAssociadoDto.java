package com.bridee.api.dto.model;

import java.util.List;

import com.bridee.api.dto.request.InformacaoAssociadoRequestDto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InformacaoAssociadoDto {
    @NotNull
    private InformacaoAssociadoRequestDto informacaoAssociado;

    // @NotNull
    // private List<ImagemAssociadoRequestDto> imagem;

    @NotNull
    private List<Integer> formasPagamento;

    @NotNull
    private List<Integer> tiposCasamento;

    @NotNull
    private List<Integer> tiposCerimonia;
}
