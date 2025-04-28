package com.bridee.api.mapper.response.externo;

import com.bridee.api.client.dto.response.PexelsImageResponseDto;
import com.bridee.api.client.dto.response.PexelsPhotos;
import com.bridee.api.dto.response.PexelsImageResponse;
import com.bridee.api.dto.response.PexelsPhotosResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PexelMapper {

    @Mapping(source = "totalResults", target = "totalResults")
    @Mapping(source = "perPage", target = "perPage")
    @Mapping(source = "nextPageUrl", target = "nextPageUrl")
    @Mapping(target = "photos", expression = "java(convertPhotos(dto))")
    PexelsImageResponse toResponse(PexelsImageResponseDto dto);

    default List<PexelsPhotosResponse> convertPhotos(PexelsImageResponseDto dto){
        List<PexelsPhotos> photos = dto.getPhotos();
        return photos.stream().map(photo -> {
            PexelsPhotosResponse response = new PexelsPhotosResponse();
            response.setId(photo.getId());
            response.setPhotographer(photo.getPhotographer());
            response.setPhotographerId(photo.getPhotographerId());
            response.setPhotographerUrl(photo.getPhotographerUrl());
            response.setAltText(photo.getAltText());
            response.setSource(photo.getSource());
            return response;
        }).toList();
    }


}
