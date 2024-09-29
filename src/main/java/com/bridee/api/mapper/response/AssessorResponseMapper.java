package com.bridee.api.mapper.response;

import com.bridee.api.dto.response.AssessorResponseDto;
import com.bridee.api.entity.Assessor;
import com.bridee.api.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AssessorResponseMapper extends BaseMapper<AssessorResponseDto, Assessor> {
}
