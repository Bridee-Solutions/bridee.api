package com.bridee.api.mapper.response;

import com.bridee.api.projection.fornecedor.AssociadoGeralResponseDto;
import com.bridee.api.projection.fornecedor.AssociadoGeralResponseProjection;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AssociadoGeralResponseMapper {

    AssociadoGeralResponseDto toGeralDto(AssociadoGeralResponseProjection projection);
}