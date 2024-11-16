package com.bridee.api.controller.impl;

import com.bridee.api.dto.request.OrcamentoFornecedorRequestDto;
import com.bridee.api.dto.response.OrcamentoFornecedorResponseDto;
import com.bridee.api.entity.OrcamentoFornecedor;
import com.bridee.api.mapper.request.OrcamentoFornecedorRequestMapper;
import com.bridee.api.mapper.response.OrcamentoFornecedorResponseMapper;
import com.bridee.api.service.OrcamentoFornecedorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orcamento-fornecedor")
@RequiredArgsConstructor
public class OrcamentoFornecedorControllerImpl {

    private final OrcamentoFornecedorService service;
    private final OrcamentoFornecedorRequestMapper requestMapper;
    private final OrcamentoFornecedorResponseMapper responseMapper;

    @PostMapping
    public ResponseEntity<List<OrcamentoFornecedorResponseDto>> associateFornecedorCasal(@RequestBody @Valid List<OrcamentoFornecedorRequestDto> requestDto){
        List<OrcamentoFornecedor> orcamentoFornecedores = requestMapper.toEntity(requestDto);
        orcamentoFornecedores = service.saveAll(orcamentoFornecedores);
        List<OrcamentoFornecedorResponseDto> responseDto = responseMapper.toDomain(orcamentoFornecedores);
        return ResponseEntity.ok(responseDto);
    }
}
