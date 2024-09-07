package com.bridee.api.client;

import com.bridee.api.dto.request.WhatsappRequestDto;
import com.bridee.api.dto.response.WhatsappResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "${services.bridee.whatsapp.url}", name = "WhatsappClient")
public interface WhatsappClient {

    @PostMapping
    ResponseEntity<WhatsappResponseDto> sendMessage(@RequestBody WhatsappRequestDto requestDto);

}
