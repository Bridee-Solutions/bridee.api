package com.bridee.api.mapper.request;

import com.bridee.api.dto.request.ImageMetadata;
import com.bridee.api.dto.response.ImagemResponseDto;
import com.bridee.api.entity.Imagem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ImageMapper {

    @Mapping(target = "data", source = "url")
    ImagemResponseDto toResponseDto(Imagem imagem);
    List<ImagemResponseDto> toResponseDto(List<Imagem> imagem);
    default Page<ImagemResponseDto> toResponseDto(Page<Imagem> page){
        return new PageImpl<>(toResponseDto(page.getContent()), page.getPageable(), page.getTotalElements());
    }

    default Imagem fromMetadata(ImageMetadata imageMetadata){
        if (imageMetadata == null){
            return null;
        }
        return Imagem.builder()
                .id(imageMetadata.getId())
                .nome(buildImagemName(imageMetadata))
                .url(imageMetadata.getUrl())
                .tipo(imageMetadata.getTipo().getValue())
                .extensao(imageMetadata.getExtensao())
                .dataCriacao(LocalDateTime.now())
                .build();
    };

    default String buildImagemName(ImageMetadata imageMetadata){
        return "%s_%s_%s.%s".formatted(imageMetadata.getNome(),
                imageMetadata.getTipo(), imageMetadata.getExtensao(), LocalDateTime.now().toString());
    }

}
