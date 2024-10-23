package com.bridee.api.mapper.request;

import com.bridee.api.client.dto.request.WhatsappRequestDto;
import com.bridee.api.client.dto.response.WhatsappResponseDto;
import com.bridee.api.client.enums.WhatsappClientType;
import com.bridee.api.pattern.observer.dto.ConviteTopicDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface WhatsappRequestMapper {

    List<WhatsappResponseDto> toRequestDto(List<ConviteTopicDto> conviteTopicDto);

    default WhatsappRequestDto toRequestDto(ConviteTopicDto conviteTopic){
        return WhatsappRequestDto.builder()
                .checkStatus("1")
                .contactPhoneNumber(conviteTopic.getConvidado().getTelefone())
                .pinCode(conviteTopic.getConvite().getPin())
                .messageBodyMimetype("image/png")
                .messageBodyFilename("qrcode.png")
                .whatsappClientType(WhatsappClientType.CONVIDADO)
                .messageType("IMAGE")
                .messageCaption("""
                        Olá, aqui está o seu convite para casamento de %s & %s
                        Atenciosamente, Equipe Bridee.
                        """.formatted(conviteTopic.getCasal().getNome(),
                        conviteTopic.getCasal().getNomeParceiro()))
                .build();
    };

}
