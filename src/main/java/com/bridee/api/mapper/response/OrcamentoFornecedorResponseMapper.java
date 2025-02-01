package com.bridee.api.mapper.response;

import com.bridee.api.dto.response.OrcamentoFornecedorResponseDto;
import com.bridee.api.entity.OrcamentoFornecedor;
import com.bridee.api.mapper.BaseMapper;
import com.bridee.api.repository.projection.orcamento.orcamentofornecedor.OrcamentoFornecedorBaseProjection;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrcamentoFornecedorResponseMapper extends BaseMapper<OrcamentoFornecedorResponseDto, OrcamentoFornecedor> {

    default List<OrcamentoFornecedor> fromProjection(List<OrcamentoFornecedorBaseProjection> projections){
        return projections.stream().map(projection -> OrcamentoFornecedor.builder()
                .id(projection.getId())
                .preco(projection.getPreco())
                .build()).toList();
    };

}
