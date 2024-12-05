package com.bridee.api.mapper.request;

import com.bridee.api.dto.model.InformacaoAssociadoDto;
import com.bridee.api.dto.request.ImageMetadata;
import com.bridee.api.dto.request.InformacaoAssociadoRequestDto;
import com.bridee.api.entity.FormaPagamento;
import com.bridee.api.entity.FormaPagamentoAssociado;
import com.bridee.api.entity.Imagem;
import com.bridee.api.entity.ImagemAssociado;
import com.bridee.api.entity.InformacaoAssociado;
import com.bridee.api.entity.TipoCasamento;
import com.bridee.api.entity.TipoCasamentoAssociado;
import com.bridee.api.entity.TipoCerimonia;
import com.bridee.api.entity.TipoCerimoniaAssociado;
import com.bridee.api.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface InformacaoAssociadoMapper extends BaseMapper<InformacaoAssociadoRequestDto, InformacaoAssociado> {

    default InformacaoAssociado toEntity(InformacaoAssociadoDto domain){
        if(domain == null){
            return null;
        }
        InformacaoAssociado informacaoAssociado = toEntity(domain.getInformacaoAssociado());
        informacaoAssociado.setTipoCasamentoAssociados(convertToTipos(domain));
        informacaoAssociado.setTipoCerimoniaAssociados(convertToCerimonias(domain));
        informacaoAssociado.setImagemAssociados(convertToImage(domain));
        informacaoAssociado.setFormaPagamentoAssociados(convertToFormaPagamentos(domain));
        return informacaoAssociado;
    }

    default List<TipoCasamentoAssociado> convertToTipos(InformacaoAssociadoDto domain){
        List<Integer> tipoCasamentoIds = domain.getTiposCasamento();
        if (tipoCasamentoIds == null){
            return null;
        }
        List<TipoCasamentoAssociado> tipoCasamentoAssociado =  new ArrayList<>();

        tipoCasamentoIds.forEach(id -> {
            TipoCasamento tipoCasamento =  TipoCasamento.builder()
                    .id(id)
                    .build();
            if (Objects.nonNull(domain.getInformacaoAssociado()) &&
                    Objects.nonNull(domain.getInformacaoAssociado().getId())){
                tipoCasamentoAssociado.add(TipoCasamentoAssociado.builder()
                        .informacaoAssociado(toEntity(domain.getInformacaoAssociado()))
                        .tipoCasamento(tipoCasamento)
                        .build());
            }
        });
        return tipoCasamentoAssociado;
    }

    default List<FormaPagamentoAssociado> convertToFormaPagamentos(InformacaoAssociadoDto domain){
        List<Integer> idsFormaPagamento = domain.getFormasPagamento();
        if (idsFormaPagamento == null){
            return null;
        }

        List<FormaPagamentoAssociado> formaPagamentoAssociados = new ArrayList<>();

        idsFormaPagamento.forEach(id -> {
            FormaPagamento formaPagamento = FormaPagamento.builder()
                    .id(id)
                    .build();
            formaPagamentoAssociados.add(FormaPagamentoAssociado.builder()
                    .formaPagamento(formaPagamento)
                    .informacaoAssociado(toEntity(domain.getInformacaoAssociado()))
                    .build());
        });
        return formaPagamentoAssociados;
    }

    default List<TipoCerimoniaAssociado> convertToCerimonias(InformacaoAssociadoDto domain){
        List<Integer> tiposCerimoniaId = domain.getTiposCerimonia();
        if (tiposCerimoniaId == null){
            return null;
        }

        List<TipoCerimoniaAssociado> tipoCerimoniasAssociado = new ArrayList<>();
        tiposCerimoniaId.forEach((id) -> {
            TipoCerimonia tipoCerimonia = TipoCerimonia.builder()
                    .id(id)
                    .build();
            tipoCerimoniasAssociado.add(TipoCerimoniaAssociado.builder()
                    .tipoCerimonia(tipoCerimonia)
                    .informacaoAssociado(toEntity(domain.getInformacaoAssociado()))
                    .build());
        });
        return tipoCerimoniasAssociado;
    }

    default List<ImagemAssociado> convertToImage(InformacaoAssociadoDto domain){
        List<ImageMetadata> imagens = domain.getImagem();
        if (imagens == null){
            return null;
        }
        ImageMapper imageMapper = new ImageMapperImpl();
        List<ImagemAssociado> imagemAssociados = new ArrayList<>();
        imagens.forEach(imagem -> {
            Imagem image = imageMapper.fromMetadata(imagem);
            imagemAssociados.add(ImagemAssociado.builder()
                    .imagem(image)
                    .informacaoAssociado(toEntity(domain.getInformacaoAssociado()))
                    .tipo(image.getTipo())
                    .build());
        });
        return imagemAssociados;
    }
}
