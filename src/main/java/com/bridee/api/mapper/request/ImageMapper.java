package com.bridee.api.mapper.request;

import com.bridee.api.dto.request.ImageMetadata;
import com.bridee.api.entity.Imagem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ImageMapper {

    default Imagem fromMetadata(ImageMetadata imageMetadata){
        if (imageMetadata == null){
            return null;
        }
        return Imagem.builder()
                .id(imageMetadata.getId())
                .nome(buildImagemName(imageMetadata))
                .tipo(imageMetadata.getTipo())
                .extensao(imageMetadata.getExtensao())
                .dataCriacao(LocalDateTime.now())
                .build();
    };

    default LocalDateTime generateDataCriacao(){
        return LocalDateTime.now();
    }

    default String buildImagemName(ImageMetadata imageMetadata){
        return "%s_%s.%s".formatted(imageMetadata.getNome(),
                imageMetadata.getTipo(), imageMetadata.getExtensao());
    }

}
