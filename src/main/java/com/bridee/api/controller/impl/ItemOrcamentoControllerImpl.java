package com.bridee.api.controller.impl;

import com.bridee.api.dto.request.ItemOrcamentoRequestDto;
import com.bridee.api.dto.response.ItemOrcamentoResponseDto;
import com.bridee.api.entity.ItemOrcamento;
import com.bridee.api.mapper.request.ItemOrcamentoRequestMapper;
import com.bridee.api.mapper.response.ItemOrcamentoResponseMapper;
import com.bridee.api.service.CustoService;
import com.bridee.api.service.ItemOrcamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/itens-orcamento")
@RequiredArgsConstructor
public class ItemOrcamentoControllerImpl {

    private final ItemOrcamentoService service;
    private final CustoService custoService;
    private final ItemOrcamentoRequestMapper requestMapper;
    private final ItemOrcamentoResponseMapper responseMapper;

    @PostMapping
    public ResponseEntity<List<ItemOrcamentoResponseDto>> vinculateItensOrcamento(@RequestBody @Valid List<ItemOrcamentoRequestDto> requestDto){
        List<ItemOrcamento> itemOrcamento = requestMapper.toEntity(requestDto);
        itemOrcamento = service.saveAllItemOrcamento(itemOrcamento);
        List<ItemOrcamentoResponseDto> responseDto = responseMapper.toDomain(itemOrcamento);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/custo/{id}")
    public ResponseEntity<Void> deleteCustoById(@PathVariable Integer id){
        custoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
