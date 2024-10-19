package com.bridee.api.mapper.request;

import com.bridee.api.dto.request.ItemOrcamentoRequestDto;
import com.bridee.api.entity.Casal;
import com.bridee.api.entity.ItemOrcamento;
import com.bridee.api.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ItemOrcamentoRequestMapper extends BaseMapper<ItemOrcamentoRequestDto, ItemOrcamento> {

    @Override
    @Mapping(target = "casal", source = "casalId", qualifiedByName = "createCasalEntity")
    ItemOrcamento toEntity(ItemOrcamentoRequestDto domain);

    @Named("createCasalEntity")
    default Casal createCasal(Integer casalId){
        return Casal.builder()
                .id(casalId)
                .build();
    }

}
