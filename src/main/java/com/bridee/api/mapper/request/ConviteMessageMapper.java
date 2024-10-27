package com.bridee.api.mapper.request;

import com.bridee.api.dto.response.CasalResponseDto;
import com.bridee.api.dto.response.ConvidadoResponseDto;
import com.bridee.api.dto.response.ConviteResponseDto;
import com.bridee.api.entity.Casal;
import com.bridee.api.entity.Convidado;
import com.bridee.api.entity.Convite;
import com.bridee.api.mapper.response.CasalResponseMapper;
import com.bridee.api.mapper.response.ConviteResponseMapper;
import com.bridee.api.pattern.observer.dto.ConviteTopicDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ConviteMessageMapper {

    default ConviteTopicDto toTopicDto(Convite convite, Casal casal, Convidado convidado){
        return ConviteTopicDto.builder()
                .convite(toConviteResponse(convite))
                .casal(toCasalResponse(casal))
                .convidado(toConvidadoResponse(convidado))
                .build();
    }

    ConviteResponseDto toConviteResponse(Convite convite);

    CasalResponseDto toCasalResponse(Casal casal);

    ConvidadoResponseDto toConvidadoResponse(Convidado convidado);

}
