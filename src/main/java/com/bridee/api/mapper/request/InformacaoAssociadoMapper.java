package com.bridee.api.mapper.request;

import com.bridee.api.dto.request.InformacaoAssociadoRequestDto;
import com.bridee.api.entity.InformacaoAssociado;
import com.bridee.api.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface InformacaoAssociadoMapper extends BaseMapper<InformacaoAssociadoRequestDto, InformacaoAssociado> {
}
