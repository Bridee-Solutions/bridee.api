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

    @Mapping(target = "nome", expression = "java(buildImagemName(imageMetadata))")
    @Mapping(target = "dataCriacao", qualifiedByName = "generateDataCriacao")
    Imagem fromMetadata(ImageMetadata imageMetadata);

    @Named("generateDataCriacao")
    default LocalDateTime generateDataCriacao(LocalDateTime localDateTime){
        return LocalDateTime.now();
    }

    default String buildImagemName(ImageMetadata imageMetadata){
        return "%s_%s.%s".formatted(imageMetadata.getNome(),
                imageMetadata.getTipo(), imageMetadata.getExtensao());
    }

}
