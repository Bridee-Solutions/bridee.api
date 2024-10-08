package com.bridee.api.mapper.request.externo;

import com.bridee.api.dto.request.externo.CasalExternoRequestDto;
import com.bridee.api.entity.Casal;
import com.bridee.api.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CasalExternoRequestMapper extends BaseMapper<CasalExternoRequestDto, Casal> {
}
