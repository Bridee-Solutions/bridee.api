package com.bridee.api.mapper.response;

import com.bridee.api.dto.response.OrcamentoFornecedorResponseDto;
import com.bridee.api.repository.projection.orcamento.orcamentofornecedor.OrcamentoFornecedorProjection;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FornecedorOrcamentoResponseMapper{

    OrcamentoFornecedorResponseDto fromProjection(OrcamentoFornecedorProjection projection);
    List<OrcamentoFornecedorResponseDto> fromProjection(List<OrcamentoFornecedorProjection> projection);

}