package com.bridee.api.dto.response;

import com.bridee.api.entity.FormaPagamento;
import com.bridee.api.entity.TipoCasamento;
import com.bridee.api.entity.TipoCerimonia;
import lombok.Data;

import java.util.List;

@Data
public class InformacaoAssociadoDetalhes {

    private List<FormaPagamento> formasPagamento;
    private List<TipoCasamento> tiposCasamento;
    private List<TipoCerimonia> tiposCerimonia;
    private ImagemResponseDto imagemPrimaria;
    private List<ImagemResponseDto> imagensSecundarias;

}
