package com.bridee.api.mapper.response;

import com.bridee.api.projection.associado.AssociadoGeralResponseDto;
import com.bridee.api.projection.associado.AssociadoGeralResponseProjection;
import com.bridee.api.projection.associado.AssociadoResponseDto;
import com.bridee.api.projection.associado.AssociadoResponseProjection;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AssociadoGeralResponseMapper {

    AssociadoGeralResponseDto toGeralDto(AssociadoGeralResponseProjection projection);

    AssociadoResponseDto toResponseDto(AssociadoResponseProjection projection);

    List<AssociadoResponseDto> toResponseDto(List<AssociadoResponseProjection> projections);
}
