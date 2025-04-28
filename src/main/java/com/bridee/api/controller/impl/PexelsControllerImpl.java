package com.bridee.api.controller.impl;

import com.bridee.api.aop.CoupleIdentifier;
import com.bridee.api.client.dto.response.PexelsImageResponseDto;
import com.bridee.api.controller.PexelsController;
import com.bridee.api.dto.request.ImageMetadata;
import com.bridee.api.dto.response.ImagemResponseDto;
import com.bridee.api.dto.response.PexelsImageResponse;
import com.bridee.api.entity.Imagem;
import com.bridee.api.mapper.request.ImageMapper;
import com.bridee.api.mapper.response.externo.PexelMapper;
import com.bridee.api.service.ImagemCasalService;
import com.bridee.api.service.PexelsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/pexels")
@RestController
@RequiredArgsConstructor
public class PexelsControllerImpl implements PexelsController {

    private final PexelsService pexelsService;
    private final ImagemCasalService imagemCasalService;
    private final ImageMapper imageMapper;
    private final PexelMapper pexelMapper;

    @GetMapping("/images")
    public ResponseEntity<PexelsImageResponse> findImages(@RequestParam String query){
        log.info("PEXELS: buscando imagens com query {}", query);
        PexelsImageResponse response = pexelMapper.toResponse(pexelsService.findPexelsImages(query));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/favoritos")
    public ResponseEntity<Void> favorite(@RequestBody ImageMetadata metadata,
                                         @CoupleIdentifier Integer casalId){
        pexelsService.favoriteImage(casalId, metadata);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/favoritos")
    public ResponseEntity<Page<ImagemResponseDto>> findFavorites(Pageable pageable,
                                                                 @CoupleIdentifier Integer casalId){
        Page<Imagem> favorites = imagemCasalService.findFavoriteImages(pageable, casalId);
        Page<ImagemResponseDto> response = imageMapper.toResponseDto(favorites);
        return ResponseEntity.ok(response);
    }

}
