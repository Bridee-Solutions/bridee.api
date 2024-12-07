package com.bridee.api.mapper.request;

import com.bridee.api.dto.request.ConvidadoRequestDto;
import com.bridee.api.entity.CategoriaConvidado;
import com.bridee.api.entity.Convidado;
import com.bridee.api.entity.Convite;
import com.bridee.api.entity.Mesa;
import com.bridee.api.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, nullValueCheckStrategy = ALWAYS)
public interface ConvidadoRequestMapper extends BaseMapper<ConvidadoRequestDto, Convidado> {

    @Override
    @Mapping(target = "mesa", source = "mesaId", qualifiedByName = "buildMesa")
    @Mapping(target= "convite", source = "conviteId",qualifiedByName = "buildConvite")
    Convidado toEntity(ConvidadoRequestDto domain);

    @Named("buildMesa")
    default Mesa buildMesa(Integer id){
        return Mesa.builder()
                .id(id)
                .build();
    }

    @Named("buildConvite")
    default Convite buildConvite(Integer id){
        return Convite.builder()
                .id(id)
                .build();
    }
}
