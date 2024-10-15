package com.bridee.api.mapper.response;

import com.bridee.api.dto.response.ConvidadoResponseDto;
import com.bridee.api.dto.response.ConvitesResponseDto;
import com.bridee.api.entity.Convidado;
import com.bridee.api.entity.Convite;
import com.bridee.api.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, nullValueCheckStrategy = ALWAYS)
public interface ConviteResponseMapper extends BaseMapper<ConvitesResponseDto, Convite> {

    @Override
    @Mapping(target = "convidados", expression = "java(convidadosToConvidadosResponseDto(entities.getConvidados()))")
    List<ConvitesResponseDto> toDomain(List<Convite> entities);

    default List<ConvidadoResponseDto> convidadosToConvidadosResponseDto(List<Convidado> convidados){
        return convidados.stream().map(ConvidadoResponseDto::new).toList();
    }

}
