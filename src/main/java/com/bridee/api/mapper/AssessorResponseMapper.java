package com.bridee.api.mapper;

import com.bridee.api.dto.response.AssessorResponseDto;
import com.bridee.api.entity.Assessor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AssessorResponseMapper extends BaseMapper<AssessorResponseDto, Assessor> {
}
