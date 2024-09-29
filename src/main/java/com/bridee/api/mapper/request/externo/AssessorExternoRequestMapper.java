package com.bridee.api.mapper.request.externo;

import com.bridee.api.dto.request.externo.AssessorExternoRequestDto;
import com.bridee.api.entity.Assessor;
import com.bridee.api.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AssessorExternoRequestMapper extends BaseMapper<AssessorExternoRequestDto, Assessor> {
}
