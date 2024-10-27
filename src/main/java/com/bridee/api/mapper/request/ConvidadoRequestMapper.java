package com.bridee.api.mapper.request;

import com.bridee.api.dto.request.ConvidadoRequestDto;
import com.bridee.api.entity.Convidado;
import com.bridee.api.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ConvidadoRequestMapper extends BaseMapper<ConvidadoRequestDto, Convidado> {
}
