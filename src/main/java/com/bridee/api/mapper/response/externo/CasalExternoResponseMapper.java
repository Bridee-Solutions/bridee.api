package com.bridee.api.mapper.response.externo;

import com.bridee.api.dto.response.externo.CasalExternoResponseDto;
import com.bridee.api.entity.Casal;
import com.bridee.api.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CasalExternoResponseMapper extends BaseMapper<CasalExternoResponseDto, Casal> {
}
