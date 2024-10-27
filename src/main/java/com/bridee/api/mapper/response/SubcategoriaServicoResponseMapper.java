package com.bridee.api.mapper.response;

import com.bridee.api.dto.response.SubcategoriaServicoResponseDto;
import com.bridee.api.entity.SubcategoriaServico;
import com.bridee.api.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SubcategoriaServicoResponseMapper extends BaseMapper<SubcategoriaServicoResponseDto, SubcategoriaServico> {
}
