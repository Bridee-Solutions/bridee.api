package com.bridee.api.mapper;

import com.bridee.api.dto.request.CasalRequestDto;
import com.bridee.api.entity.Casal;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CasalRequestMapper extends BaseMapper<CasalRequestDto, Casal> {
}
