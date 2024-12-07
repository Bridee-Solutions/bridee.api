package com.bridee.api.mapper.response;

import com.bridee.api.dto.response.CategoriaConvidadoResponseDto;
import com.bridee.api.dto.response.CategoriaConvidadoResumoDto;
import com.bridee.api.entity.CategoriaConvidado;
import com.bridee.api.entity.enums.CategoriaConvidadoEnum;
import com.bridee.api.mapper.BaseMapper;
import com.bridee.api.projection.convite.CategoriaConvidadoProjection;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoriaConvidadoResponseMapper extends BaseMapper<CategoriaConvidadoResponseDto, CategoriaConvidado> {

    default CategoriaConvidadoResumoDto fromProjection(CategoriaConvidadoProjection projection, CategoriaConvidadoEnum categoriaConvidado){
        if (projection == null){
            return null;
        }
        return new CategoriaConvidadoResumoDto(categoriaConvidado.name(), projection.getTotal());
    }

}
