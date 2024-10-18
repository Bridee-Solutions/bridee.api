package com.bridee.api.mapper.request;

import com.bridee.api.dto.request.AvaliacaoRequestDto;
import com.bridee.api.entity.Avaliacao;
import com.bridee.api.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AvaliacaoRequestMapper extends BaseMapper<AvaliacaoRequestDto, Avaliacao> {
}
