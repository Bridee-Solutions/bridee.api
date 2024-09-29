package com.bridee.api.mapper.response.externo;

import com.bridee.api.dto.response.externo.AssessorExternoResponseDto;
import com.bridee.api.entity.Assessor;
import com.bridee.api.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AssessorExternoResponseMapper extends BaseMapper<AssessorExternoResponseDto, Assessor> {
}
