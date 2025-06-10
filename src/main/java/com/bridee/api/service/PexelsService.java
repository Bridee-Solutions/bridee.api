package com.bridee.api.service;

import com.bridee.api.client.PexelsClient;
import com.bridee.api.client.dto.response.PexelsImageResponseDto;
import com.bridee.api.client.dto.response.PexelsPhotos;
import com.bridee.api.configuration.cache.CacheConstants;
import com.bridee.api.dto.request.ImageMetadata;
import com.bridee.api.dto.request.PexelsRequestDto;
import com.bridee.api.entity.Imagem;
import com.bridee.api.exception.ImagesNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PexelsService {

    private final PexelsClient pexelsClient;
    private final ImagemCasalService imagemCasalService;
    @Value("${services.bridee.pexels.apiKey}")
    private String apiKey;

    public PexelsImageResponseDto findPexelsImages(PexelsRequestDto query, Integer casalId){
        Map<String, String> queryFilter = query.getFilter();
        ResponseEntity<PexelsImageResponseDto> images = pexelsClient.getImages(queryFilter, apiKey);
        PexelsImageResponseDto responseDto = Objects.nonNull(images) ? images.getBody() : null;

        validatePexelsResponse(responseDto, query.getQuery());
        verifyCoupleFavoriteImages(responseDto, casalId);
        log.info("PEXELS: {} imagens encontradas", responseDto.getTotalResults());

        return responseDto;
    }

    private void validatePexelsResponse(PexelsImageResponseDto responseDto, String query){
        if (Objects.isNull(responseDto)) {
            log.error("PEXELS: nenhuma imagem encontrada para a query {}", query);
            throw new ImagesNotFoundException();
        }
    }

    private void verifyCoupleFavoriteImages(PexelsImageResponseDto responseDto, Integer casalId) {
        List<Imagem> allFavoriteImages = imagemCasalService.findAllFavoriteImages(casalId);
        Map<String, List<Imagem>> imageMap = allFavoriteImages
                .stream()
                .collect(Collectors.groupingBy(Imagem::getUrl));
        responseDto.getPhotos().forEach(photo -> {
            findFavoriteImages(photo, imageMap);
        });
    }

    private void findFavoriteImages(PexelsPhotos photo,
                                    Map<String, List<Imagem>> imageMap){
        photo.getSource().findAllUrls().forEach(url -> {
            if(imageMap.containsKey(url)){
                List<Imagem> imagems = imageMap.get(url);
                photo.setFavorite(true);
                photo.setId(imagems.get(0).getId().longValue());
            }
        });
    }

    @CacheEvict(cacheNames = CacheConstants.FAVORITE_IMAGE, allEntries = true)
    public Imagem favoriteImage(Integer casalId, ImageMetadata imageMetadata){
        return imagemCasalService.favoriteImage(casalId, imageMetadata);
    }

    @CacheEvict(cacheNames = CacheConstants.FAVORITE_IMAGE, allEntries = true)
    public void desfavorite(Integer id, Integer casalId){
        imagemCasalService.desfavorite(id, casalId);
    }

}
