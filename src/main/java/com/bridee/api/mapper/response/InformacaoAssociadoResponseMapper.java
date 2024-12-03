package com.bridee.api.mapper.response;

import com.bridee.api.mapper.BaseMapper;
import org.mapstruct.Mapper;

import com.bridee.api.dto.response.InformacaoAssociadoResponseDto;
import com.bridee.api.entity.InformacaoAssociado;

@Mapper(componentModel = "spring")
public interface InformacaoAssociadoResponseMapper extends BaseMapper<InformacaoAssociadoResponseDto, InformacaoAssociado> {
}
