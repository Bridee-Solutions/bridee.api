package com.bridee.api.controller.impl;

import com.bridee.api.client.dto.response.PexelsImageResponseDto;
import com.bridee.api.controller.PexelsController;
import com.bridee.api.service.PexelsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/pexels")
@RestController
@RequiredArgsConstructor
public class PexelsControllerImpl implements PexelsController {

    private final PexelsService pexelsService;

    @GetMapping("/images")
    public ResponseEntity<PexelsImageResponseDto> findImages(@RequestParam String query){
        log.info("PEXELS: buscando imagens com query {}", query);
        return ResponseEntity.ok(pexelsService.findPexelsImages(query));
    }

}
