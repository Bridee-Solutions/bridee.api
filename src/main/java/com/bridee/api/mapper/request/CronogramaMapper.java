package com.bridee.api.mapper.request;

import com.bridee.api.dto.request.CronogramaRequestDto;
import com.bridee.api.entity.Casamento;
import com.bridee.api.entity.Cronograma;
import com.bridee.api.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.Objects;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CronogramaMapper extends BaseMapper<CronogramaRequestDto, Cronograma> {

    @Override
    @Mapping(target = "casamento", source = "casamentoId", qualifiedByName = "createCasamento")
    Cronograma toEntity(CronogramaRequestDto domain);

    @Named("createCasamento")
    default Casamento createCasamento(Integer casamentoId){
        if(Objects.isNull(casamentoId)){
            return null;
        }
        return Casamento.builder()
                .id(casamentoId)
                .build();
    }
}
