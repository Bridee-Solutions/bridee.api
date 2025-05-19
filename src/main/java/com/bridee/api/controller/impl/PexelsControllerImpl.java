package com.bridee.api.controller.impl;

import com.bridee.api.aop.CoupleIdentifier;
import com.bridee.api.client.dto.response.PexelsImageResponseDto;
import com.bridee.api.controller.PexelsController;
import com.bridee.api.dto.request.ImageMetadata;
import com.bridee.api.dto.request.PexelsRequestDto;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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
    public ResponseEntity<PexelsImageResponse> findImages(@RequestParam String query,
                                                          @RequestParam String page,
                                                          @RequestParam String perPage,
                                                          @CoupleIdentifier Integer casalId){
        PexelsRequestDto requestDto = new PexelsRequestDto(query, perPage, page);
        log.info("PEXELS: buscando imagens com query {}", query);
        PexelsImageResponseDto pexelsImages = pexelsService.findPexelsImages(requestDto, casalId);
        PexelsImageResponse response = pexelMapper.toResponse(pexelsImages);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/favoritos")
    public ResponseEntity<ImagemResponseDto> favorite(@RequestBody ImageMetadata metadata,
                                         @CoupleIdentifier Integer casalId){
        Imagem imagem = pexelsService.favoriteImage(casalId, metadata);
        ImagemResponseDto responseDto = imageMapper.toResponseDto(imagem);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/desfavoritar/{id}")
    public ResponseEntity<Void> desfavoritar(@PathVariable Integer id,
                                             @CoupleIdentifier Integer casalId){
        pexelsService.desfavorite(id, casalId);
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
