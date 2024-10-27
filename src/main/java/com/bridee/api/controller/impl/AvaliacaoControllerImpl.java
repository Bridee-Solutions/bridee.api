package com.bridee.api.controller.impl;

import com.bridee.api.controller.AvaliacaoController;
import com.bridee.api.dto.request.AvaliacaoRequestDto;
import com.bridee.api.entity.Avaliacao;
import com.bridee.api.mapper.request.AvaliacaoRequestMapper;
import com.bridee.api.service.AvaliacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/avaliacoes")
@RequiredArgsConstructor
public class AvaliacaoControllerImpl implements AvaliacaoController {

    private final AvaliacaoService service;
    private final AvaliacaoRequestMapper requestMapper;

    @PostMapping
    public ResponseEntity<Void> saveAvaliacao(@RequestBody @Valid AvaliacaoRequestDto requestDto){
        Avaliacao avaliacao = requestMapper.toEntity(requestDto);
        service.save(avaliacao);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
