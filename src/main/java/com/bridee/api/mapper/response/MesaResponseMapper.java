package com.bridee.api.mapper.response;

import com.bridee.api.dto.response.MesaResponseDto;
import com.bridee.api.entity.Mesa;
import com.bridee.api.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MesaResponseMapper extends BaseMapper<MesaResponseDto, Mesa> {
}
