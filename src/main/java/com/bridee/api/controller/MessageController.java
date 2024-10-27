package com.bridee.api.controller;

import com.bridee.api.client.dto.request.WhatsappRequestDto;
import com.bridee.api.client.dto.response.WhatsappResponseDto;
import com.bridee.api.dto.request.ConviteMessageDto;
import com.bridee.api.dto.request.EmailDto;
import com.bridee.api.dto.response.ErrorResponseDto;
import com.google.zxing.WriterException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

@Tag(name = "Controller de mensagem")
public interface MessageController {

    @Operation(summary = "Envio de mensagens por whatsapp",
            description = "Envio de mensagens através do whatsapp")
    @ApiResponse(responseCode = "200", description = "Mesagem enviada com sucesso")
    ResponseEntity<WhatsappResponseDto> sendWhatsappMessage(@RequestBody WhatsappRequestDto whatsappRequestDto) throws IOException, WriterException;

    @Operation(summary = "Envio de mensagens por whatsapp",
            description = "Envio de mensagens através do whatsapp")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mesagem enviada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Informações para gerar o convite não foram encontradas",
                    content = @Content(schema =  @Schema(implementation = ErrorResponseDto.class)))
    })
    ResponseEntity<Void> sendConvitesWhatsappMessages(@RequestBody @Valid ConviteMessageDto conviteMessageDto);

}
