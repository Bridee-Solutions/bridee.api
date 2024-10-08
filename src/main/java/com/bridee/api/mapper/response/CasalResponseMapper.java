package com.bridee.api.mapper.response;

import com.bridee.api.dto.response.CasalResponseDto;
import com.bridee.api.entity.Casal;
import com.bridee.api.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CasalResponseMapper extends BaseMapper<CasalResponseDto, Casal> {
}
