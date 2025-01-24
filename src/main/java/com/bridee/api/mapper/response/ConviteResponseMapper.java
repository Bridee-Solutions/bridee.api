package com.bridee.api.mapper.response;

import com.bridee.api.dto.response.*;
import com.bridee.api.entity.Convidado;
import com.bridee.api.entity.Convite;
import com.bridee.api.mapper.BaseMapper;
import com.bridee.api.projection.convite.ConviteResumoProjection;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, nullValueCheckStrategy = ALWAYS)
public interface ConviteResponseMapper extends BaseMapper<ConvitesResponseDto, Convite> {

    ConviteResponseDto toConviteResponse(Convite convite);

    default List<ConvidadoResponseDto> convidadosToConvidadosResponseDto(List<Convidado> convidados){
        return convidados.stream().map(ConvidadoResponseDto::new).toList();
    }

    default ConviteResumoResponseDto fromProjection(ConviteResumoProjection projection, List<CategoriaConvidadoResumoDto> categoriaConvidadoResumo){
        if(projection == null){
            return null;
        }
        return ConviteResumoResponseDto.builder()
                .totalConvites(projection.getTotalConvites())
                .totalConfirmado(projection.getTotalConfirmado())
                .totalConvidados(projection.getTotalConvidados())
                .totalCriancas(projection.getTotalCriancas())
                .totalAdultos(projection.getTotalAdultos())
                .resumoCategorias(categoriaConvidadoResumo)
                .build();
    }

}
