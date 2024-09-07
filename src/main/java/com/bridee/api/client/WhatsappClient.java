package com.bridee.api.client;

import com.bridee.api.client.dto.request.WhatsappRequestDto;
import com.bridee.api.client.dto.response.WhatsappResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "${services.bridee.whatsapp.url}", name = "WhatsappClient")
public interface WhatsappClient {

    @PostMapping("/Send")
    ResponseEntity<WhatsappResponseDto> sendMessage(@RequestBody WhatsappRequestDto requestDto);

}
