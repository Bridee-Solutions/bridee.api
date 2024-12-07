package com.bridee.api.mapper.response;

import com.bridee.api.dto.response.ItemOrcamentoResponseDto;
import com.bridee.api.entity.ItemOrcamento;
import com.bridee.api.mapper.BaseMapper;
import com.bridee.api.projection.orcamento.ItemOrcamentoProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ItemOrcamentoResponseMapper extends BaseMapper<ItemOrcamentoResponseDto, ItemOrcamento> {

    @Mapping(target = "custos", ignore = true)
    ItemOrcamento fromProjection(ItemOrcamentoProjection itemOrcamentoProjection);

    List<ItemOrcamento> fromProjection(List<ItemOrcamentoProjection> itensOrcamentoProjection);

}
