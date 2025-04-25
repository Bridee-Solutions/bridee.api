package com.bridee.api.service;

import com.bridee.api.client.PexelsClient;
import com.bridee.api.client.dto.response.PexelsImageResponseDto;
import com.bridee.api.dto.request.ImageMetadata;
import com.bridee.api.exception.ImagesNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PexelsService {

    private final PexelsClient pexelsClient;
    private final ImagemCasalService imagemCasalService;
    @Value("${services.bridee.pexels.apiKey}")
    private String apiKey;

    public PexelsImageResponseDto findPexelsImages(String query){
        ResponseEntity<PexelsImageResponseDto> images = pexelsClient.getImages(query, apiKey);
        PexelsImageResponseDto responseDto = Objects.nonNull(images) ? images.getBody() : null;
        if (Objects.isNull(responseDto)) {
            log.error("PEXELS: nenhuma imagem encontrada para a query {}", query);
            throw new ImagesNotFoundException();
        }
        log.info("PEXELS: {} imagens encontradas", responseDto.getTotalResults());
        return responseDto;
    }

    public void favoriteImage(Integer casalId, ImageMetadata imageMetadata){
        imagemCasalService.favoriteImage(casalId,imageMetadata);
    }

}
