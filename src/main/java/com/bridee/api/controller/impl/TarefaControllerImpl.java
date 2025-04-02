package com.bridee.api.controller.impl;

import com.bridee.api.aop.WeddingIdentifier;
import com.bridee.api.dto.request.TarefaRequestDto;
import com.bridee.api.dto.response.TarefaResponseDto;
import com.bridee.api.dto.response.TarefasResponseDto;
import com.bridee.api.entity.Tarefa;
import com.bridee.api.mapper.request.TarefaRequestMapper;
import com.bridee.api.mapper.response.TarefaResponseMapper;
import com.bridee.api.mapper.response.TarefasResponseMapper;
import com.bridee.api.service.TarefaService;
import com.bridee.api.utils.UriUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor
public class TarefaControllerImpl {

    private final TarefaService service;
    private final TarefaRequestMapper requestMapper;
    private final TarefaResponseMapper responseMapper;
    private final TarefasResponseMapper tarefasResponseMapper;

    @GetMapping
    public ResponseEntity<List<TarefasResponseDto>> findAll(@WeddingIdentifier Integer casamentoId,
                                                            @RequestParam Map<String, Object> params){
        log.info("TAREFA: buscando as tarefas do casamento de id {}", casamentoId);
        List<Tarefa> tarefas = service.findAllByCasalId(casamentoId, params);
        return ResponseEntity.ok(tarefasResponseMapper.toTarefasDto(tarefas));
    }

    @PostMapping
    public ResponseEntity<TarefaResponseDto> save(@WeddingIdentifier Integer casamentoId,
                                                  @RequestBody @Valid TarefaRequestDto requestDto){
        log.info("TAREFA: buscando tarefas do casamento com id {}", casamentoId);
        Tarefa tarefa = requestMapper.toEntity(requestDto);
        tarefa = service.save(casamentoId, tarefa);
        TarefaResponseDto responseDto = responseMapper.toDomain(tarefa);
        return ResponseEntity.created(UriUtils.uriBuilder(responseDto.getId())).body(responseDto);
    }

    @PutMapping("/{tarefaId}")
    public ResponseEntity<TarefaResponseDto> update(@WeddingIdentifier Integer casamentoId,
                                                    @PathVariable Integer tarefaId,
                                                    @RequestBody @Valid TarefaRequestDto requestDto){
        log.info("TAREFA: atualizando tarefa de id {}", tarefaId);
        Tarefa tarefa = requestMapper.toEntity(requestDto);
        tarefa = service.update(tarefa, tarefaId, casamentoId);
        TarefaResponseDto responseDto = responseMapper.toDomain(tarefa);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TarefaResponseDto> delete(@PathVariable Integer id){
        log.info("TAREFA: deletando tarefa de id {}", id);
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
