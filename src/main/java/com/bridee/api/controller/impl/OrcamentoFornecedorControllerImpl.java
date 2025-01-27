package com.bridee.api.controller.impl;

import com.bridee.api.dto.request.AssociadoPrecoRequestDto;
import com.bridee.api.dto.request.OrcamentoFornecedorRequestDto;
import com.bridee.api.dto.response.OrcamentoFornecedorResponseDto;
import com.bridee.api.entity.Casal;
import com.bridee.api.entity.Casamento;
import com.bridee.api.entity.OrcamentoFornecedor;
import com.bridee.api.mapper.request.OrcamentoFornecedorRequestMapper;
import com.bridee.api.mapper.response.OrcamentoFornecedorResponseMapper;
import com.bridee.api.service.CasamentoService;
import com.bridee.api.service.OrcamentoFornecedorService;
import com.bridee.api.utils.UriUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    private final CasamentoService casamentoService;

    @PostMapping("/{casamentoId}")
    public ResponseEntity<List<OrcamentoFornecedorResponseDto>> associateFornecedoresCasal(@RequestBody @Valid List<OrcamentoFornecedorRequestDto> requestDto,
                                                                                           @PathVariable Integer casamentoId){
        Casamento casamento = casamentoService.findById(casamentoId);
        Casal casal = casamento.getCasal();
        List<OrcamentoFornecedor> orcamentoFornecedores = requestMapper.toEntity(requestDto, casal);
        orcamentoFornecedores = service.saveAll(orcamentoFornecedores);
        List<OrcamentoFornecedorResponseDto> responseDto = responseMapper.toDomain(orcamentoFornecedores);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/categoria/{categoriaId}/casamento/{casamentoId}")
    public ResponseEntity<OrcamentoFornecedorResponseDto> saveOrcamentoFornecedorCasal(@RequestBody @Valid OrcamentoFornecedorRequestDto requestDto,
                                                                                       @PathVariable Integer categoriaId,
                                                                                       @PathVariable Integer casamentoId){
        Casamento casamento = casamentoService.findById(casamentoId);
        Casal casal = casamento.getCasal();
        OrcamentoFornecedor orcamentoFornecedor = requestMapper.toEntity(requestDto);
        orcamentoFornecedor = service.saveOrcamentoFornecedorCasal(orcamentoFornecedor, categoriaId);
        OrcamentoFornecedorResponseDto responseDto = responseMapper.toDomain(orcamentoFornecedor);
        return ResponseEntity.created(UriUtils.uriBuilder(responseDto.getId())).body(responseDto);
    }

    @PutMapping("/preco/{id}")
    public ResponseEntity<Void> updateOrcamentoFornecedor(@PathVariable Integer id,
                                                          @RequestBody @Valid AssociadoPrecoRequestDto requestDto){
        service.updateOrcamentoFornecedorPreco(id, requestDto.getPreco());
        return ResponseEntity.noContent().build();
    }
}
