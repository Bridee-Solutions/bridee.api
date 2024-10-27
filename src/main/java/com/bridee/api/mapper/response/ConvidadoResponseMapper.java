package com.bridee.api.mapper.response;

import com.bridee.api.dto.response.ConvidadoResponseDto;
import com.bridee.api.entity.Convidado;
import com.bridee.api.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ConvidadoResponseMapper extends BaseMapper<ConvidadoResponseDto, Convidado> {
}
