package com.bridee.api.mapper.response;

import com.bridee.api.dto.response.CategoriaServicoResponseDto;
import com.bridee.api.entity.CategoriaServico;
import com.bridee.api.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoriaServicoResponseMapper extends BaseMapper<CategoriaServicoResponseDto, CategoriaServico> {
}
