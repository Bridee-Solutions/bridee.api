package com.bridee.api.mapper.response;

import com.bridee.api.dto.response.CasalOrcamentoResponseDto;
import com.bridee.api.entity.Casal;
import com.bridee.api.mapper.BaseMapper;
import com.bridee.api.repository.projection.orcamento.OrcamentoProjection;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CasalOrcamentoResponseMapper extends BaseMapper<CasalOrcamentoResponseDto, Casal> {

    @Override
    CasalOrcamentoResponseDto toDomain(Casal entity);

    CasalOrcamentoResponseDto toOrcamentoResponse(OrcamentoProjection projection);

}
