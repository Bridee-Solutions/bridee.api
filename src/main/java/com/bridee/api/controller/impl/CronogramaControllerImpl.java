package com.bridee.api.controller.impl;

import com.bridee.api.dto.request.CronogramaRequestDto;
import com.bridee.api.entity.Cronograma;
import com.bridee.api.mapper.request.CronogramaMapper;
import com.bridee.api.service.CronogramaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/cronogramas")
@RequiredArgsConstructor
public class CronogramaControllerImpl {

    private final CronogramaService service;
    private final CronogramaMapper mapper;

    @PostMapping
    public ResponseEntity<Cronograma> save(@RequestBody @Valid CronogramaRequestDto request){
        log.info("CRONOGRAMA: persistindo o cronograma e suas atividades");
        Cronograma cronograma = mapper.toEntity(request);
        return ResponseEntity.ok(service.save(cronograma));
    }

}
