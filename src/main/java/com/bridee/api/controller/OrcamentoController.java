package com.bridee.api.controller;

import com.bridee.api.dto.request.OrcamentoCasalRequestDto;
import com.bridee.api.dto.response.CasalOrcamentoResponseDto;
import com.bridee.api.entity.Casal;
import com.bridee.api.mapper.response.CasalOrcamentoResponseMapper;
import com.bridee.api.projection.orcamento.OrcamentoFornecedorProjection;
import com.bridee.api.projection.orcamento.OrcamentoProjection;
import com.bridee.api.service.OrcamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orcamentos")
@RequiredArgsConstructor
public class OrcamentoController {

    private final CasalOrcamentoResponseMapper casalOrcamentoResponseMapper;
    private final OrcamentoService orcamentoService;

    @GetMapping("/casal/{id}")
    public ResponseEntity<OrcamentoProjection> findOrcamentoCasal(@PathVariable Integer id){
        OrcamentoProjection projection = orcamentoService.findCasalOrcamento(id);
        return ResponseEntity.ok(projection);
    }

    @PostMapping("/casal")
    public ResponseEntity<CasalOrcamentoResponseDto> saveItemOrcamento(@RequestBody @Valid OrcamentoCasalRequestDto requestDto){
        Casal casal = orcamentoService.saveOrcamentoCasal(requestDto);
        return ResponseEntity.ok(casalOrcamentoResponseMapper.toDomain(casal));
    }
    
}
