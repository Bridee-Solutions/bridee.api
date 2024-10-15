package com.bridee.api.controller;

import com.bridee.api.dto.response.ConvitesResponseDto;
import com.bridee.api.entity.Convite;
import com.bridee.api.mapper.response.ConviteResponseMapper;
import com.bridee.api.service.ConviteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/convites")
@RequiredArgsConstructor
public class ConviteController {

    private final ConviteService conviteService;
    private final ConviteResponseMapper responseMapper;

    //TODO: Aplicar Specification para filtrar os status e outras informações do convite / convidados.
    @GetMapping("/{casamentoId}")
    public ResponseEntity<Page<ConvitesResponseDto>> findAllInvites(@PathVariable Integer casamentoId){
        List<Convite> convites = conviteService.findAllByCasamentoId(casamentoId);
        List<ConvitesResponseDto> convitesResponse = responseMapper.toDomain(convites);
        return ResponseEntity.ok(responseMapper.toPage(convitesResponse));
    }

}
