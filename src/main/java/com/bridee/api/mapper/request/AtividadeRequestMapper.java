package com.bridee.api.mapper.request;

import com.bridee.api.dto.request.AtividadeRequestDto;
import com.bridee.api.entity.Atividade;
import com.bridee.api.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AtividadeRequestMapper extends BaseMapper<AtividadeRequestDto, Atividade> {
}
