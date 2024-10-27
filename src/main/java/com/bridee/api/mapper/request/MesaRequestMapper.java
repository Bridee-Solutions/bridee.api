package com.bridee.api.mapper.request;

import com.bridee.api.dto.request.MesaRequestDto;
import com.bridee.api.entity.Mesa;
import com.bridee.api.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MesaRequestMapper extends BaseMapper<MesaRequestDto, Mesa> {
}
