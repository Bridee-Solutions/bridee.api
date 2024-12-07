package com.bridee.api.mapper.response;

import com.bridee.api.dto.response.AtividadeResponseDto;
import com.bridee.api.entity.Atividade;
import com.bridee.api.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AtividadeResponseMapper extends BaseMapper<AtividadeResponseDto, Atividade>{
}
