package com.bridee.api.controller.impl;

import com.bridee.api.client.dto.response.PexelsImageResponseDto;
import com.bridee.api.service.PexelsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/pexels")
@RestController
@RequiredArgsConstructor
public class PexelsControllerImpl {

    private final PexelsService pexelsService;

    @GetMapping("/images")
    public ResponseEntity<PexelsImageResponseDto> findImages(@RequestParam String query){
        return ResponseEntity.ok(pexelsService.findPexelsImages(query));
    }

}
