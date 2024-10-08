package com.bridee.api.mapper.request;

import com.bridee.api.dto.request.AssessorRequestDto;
import com.bridee.api.entity.Assessor;
import com.bridee.api.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AssessorRequestMapper extends BaseMapper<AssessorRequestDto, Assessor> {
}
