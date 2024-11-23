package com.bridee.api.mapper.response;

import com.bridee.api.dto.response.CasamentoResponseDto;
import com.bridee.api.entity.Casamento;
import com.bridee.api.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CasamentoResponseMapper extends BaseMapper<CasamentoResponseDto, Casamento> {
}
