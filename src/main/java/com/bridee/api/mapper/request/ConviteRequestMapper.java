package com.bridee.api.mapper.request;

import com.bridee.api.dto.request.ConviteRequestDto;
import com.bridee.api.entity.Casamento;
import com.bridee.api.entity.Convite;
import com.bridee.api.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ConviteRequestMapper extends BaseMapper<ConviteRequestDto, Convite> {

    @Override
    @Mapping(target = "casamento", source = "casamentoId", qualifiedByName = "createCasamentoId")
    Convite toEntity(ConviteRequestDto domain);

    @Named("createCasamentoId")
    default Casamento createCasamento(Integer casamentoId){
        return Casamento.builder()
                .id(casamentoId)
                .build();
    }
}
