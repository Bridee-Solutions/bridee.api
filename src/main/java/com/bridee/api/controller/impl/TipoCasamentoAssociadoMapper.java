package com.bridee.api.controller.impl;
import com.bridee.api.dto.request.InformacaoAssociadoRequestDto;
import com.bridee.api.dto.request.TipoCasamentoAssociadoRequestDto;
import com.bridee.api.entity.InformacaoAssociado;
import com.bridee.api.entity.TipoCasamentoAssociado;
import com.bridee.api.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TipoCasamentoAssociadoMapper extends BaseMapper<TipoCasamentoAssociadoRequestDto, TipoCasamentoAssociado> {
}
