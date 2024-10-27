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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PexelsService {

    private final PexelsClient pexelsClient;
    @Value("${services.bridee.pexels.apiKey}")
    private String apiKey;

    public PexelsImageResponseDto findPexelsImages(String query){
        ResponseEntity<PexelsImageResponseDto> images = pexelsClient.getImages(query, apiKey);
        PexelsImageResponseDto responseDto = images.getBody() != null ? images.getBody() : null;
        if (responseDto == null) throw new ImagesNotFoundException();

        ListaObj<PexelsPhotos> photos = new ListaObj<>();
        responseDto.getPhotos().forEach(photos::add);
        List<PexelsPhotos> list = new ArrayList<>();
        Comparable<PexelsPhotos>[] pexelsPhotos = MergeSort.mergeSort(photos);
        for (Comparable<PexelsPhotos> pexel: pexelsPhotos){
            list.add((PexelsPhotos) pexel);
        }
        responseDto.setPhotos(list);
        return responseDto;
    };

}
