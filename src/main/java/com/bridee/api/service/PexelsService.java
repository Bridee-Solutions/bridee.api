package com.bridee.api.service;

import com.bridee.api.client.PexelsClient;
import com.bridee.api.client.dto.response.PexelsImageResponseDto;
import com.bridee.api.client.dto.response.PexelsPhotos;
import com.bridee.api.exception.ImagesNotFoundException;
import com.bridee.api.utils.ListaObj;
import com.bridee.api.utils.MergeSort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PexelsService {

    private final PexelsClient pexelsClient;
    @Value("${services.bridee.pexels.apiKey}")
    private String apiKey;

    public PexelsImageResponseDto findPexelsImages(String query){
        ResponseEntity<PexelsImageResponseDto> images = pexelsClient.getImages(query, apiKey);
        PexelsImageResponseDto responseDto = Objects.nonNull(images) ? images.getBody() : null;
        if (responseDto == null) throw new ImagesNotFoundException();
        return responseDto;
    };

}
