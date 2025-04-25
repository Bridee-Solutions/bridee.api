package com.bridee.api.controller.impl;

import com.bridee.api.aop.CoupleIdentifier;
import com.bridee.api.controller.OrcamentoController;
import com.bridee.api.dto.request.OrcamentoCasalRequestDto;
import com.bridee.api.dto.response.CasalOrcamentoResponseDto;
import com.bridee.api.entity.Casal;
import com.bridee.api.mapper.response.CasalOrcamentoResponseMapper;
import com.bridee.api.repository.projection.orcamento.OrcamentoProjection;
import com.bridee.api.service.OrcamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/orcamentos")
@RequiredArgsConstructor
public class OrcamentoControllerImpl implements OrcamentoController {

    private final CasalOrcamentoResponseMapper casalOrcamentoResponseMapper;
    private final OrcamentoService orcamentoService;

    @GetMapping("/casamento}")
    public ResponseEntity<OrcamentoProjection> findOrcamentoCasal(@CoupleIdentifier Integer casalId){
        log.info("ORCAMENTO: buscando orcamento do casal de id {}", casalId);
        OrcamentoProjection projection = orcamentoService.findCasalOrcamento(casalId);
        return ResponseEntity.ok(projection);
    }

    @GetMapping(value = "/csv/casamento", produces = "text/plain")
    public ResponseEntity<byte[]> downloadOrcamentoCsv(@CoupleIdentifier Integer casalId){
        log.info("ORCAMENTO: criando csv com dados relacionados ao orcamento do casamento");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_PLAIN);
        httpHeaders.setContentDispositionFormData("attachment", "orcamento.csv");

        return new ResponseEntity<>(orcamentoService.generateCsvFile(casalId), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/casal")
    public ResponseEntity<CasalOrcamentoResponseDto> saveItemOrcamento(@RequestBody @Valid OrcamentoCasalRequestDto requestDto){
        log.info("ORCAMENTO: salvando itens do orçamento");
        Casal casal = orcamentoService.saveOrcamentoCasal(requestDto);
        return ResponseEntity.ok(casalOrcamentoResponseMapper.toDomain(casal));
    }
    
}
