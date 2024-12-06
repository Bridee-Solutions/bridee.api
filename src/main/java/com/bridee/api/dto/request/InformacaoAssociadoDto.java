package com.bridee.api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class InformacaoAssociadoDto {

    @NotNull
    private InformacaoAssociadoRequestDto informacaoAssociado;

    private List<ImageMetadata> imagem;

    private List<Integer> formasPagamento;

    private List<Integer> tiposCasamento;

    private List<Integer> tiposCerimonia;

}
