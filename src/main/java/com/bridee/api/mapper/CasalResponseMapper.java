package com.bridee.api.mapper;

import com.bridee.api.dto.response.CasalResponseDto;
import com.bridee.api.entity.Casal;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CasalResponseMapper extends BaseMapper<CasalResponseDto, Casal>{
}
