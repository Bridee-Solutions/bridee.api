package com.bridee.api.mapper.response;

import com.bridee.api.dto.response.CasalOrcamentoResponseDto;
import com.bridee.api.dto.response.ItemOrcamentoResponseDto;
import com.bridee.api.dto.response.OrcamentoFornecedorResponseDto;
import com.bridee.api.entity.Casal;
import com.bridee.api.entity.ItemOrcamento;
import com.bridee.api.entity.OrcamentoFornecedor;
import com.bridee.api.mapper.BaseMapper;
import com.bridee.api.projection.orcamento.OrcamentoFornecedorProjection;
import com.bridee.api.projection.orcamento.OrcamentoProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CasalOrcamentoResponseMapper extends BaseMapper<CasalOrcamentoResponseDto, Casal> {

    @Override
    CasalOrcamentoResponseDto toDomain(Casal entity);

    CasalOrcamentoResponseDto toOrcamentoResponse(OrcamentoProjection projection);

}
