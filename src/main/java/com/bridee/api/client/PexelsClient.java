package com.bridee.api.client;

import com.bridee.api.client.dto.response.PexelsImageResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(url = "${services.bridee.pexels.url}", name = "IntegracaoPexels")
public interface PexelsClient {

    @GetMapping("/v1/search")
    ResponseEntity<PexelsImageResponseDto> getImages(@RequestParam String query, @RequestHeader("Authorization") String apiKey);
}