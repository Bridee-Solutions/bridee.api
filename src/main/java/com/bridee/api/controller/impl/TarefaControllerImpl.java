package com.bridee.api.controller.impl;

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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor
public class TarefaControllerImpl {

    private final TarefaService service;
    private final TarefaRequestMapper requestMapper;
    private final TarefaResponseMapper responseMapper;
    private final TarefasResponseMapper tarefasResponseMapper;

    @GetMapping("/casamento/{casamentoId}")
    public ResponseEntity<List<TarefasResponseDto>> findAll(@PathVariable Integer casamentoId,
                                                            @RequestParam Map<String, Object> params){
        List<Tarefa> tarefas = service.findAllByCasalId(casamentoId, params);
        return ResponseEntity.ok(tarefasResponseMapper.toTarefasDto(tarefas));
    }

    @PostMapping("/casamento/{casamentoId}")
    public ResponseEntity<TarefaResponseDto> save(@PathVariable Integer casamentoId,
                                                  @RequestBody @Valid TarefaRequestDto requestDto){
        Tarefa tarefa = requestMapper.toEntity(requestDto);
        tarefa = service.save(casamentoId, tarefa);
        TarefaResponseDto responseDto = responseMapper.toDomain(tarefa);
        return ResponseEntity.created(UriUtils.uriBuilder(responseDto.getId())).body(responseDto);
    }

    @PutMapping("/casamento/{casamentoId}/tarefa/{tarefaId}")
    public ResponseEntity<TarefaResponseDto> update(@PathVariable Integer casamentoId,
                                                    @PathVariable Integer tarefaId,
                                                    @RequestBody @Valid TarefaRequestDto requestDto){
        Tarefa tarefa = requestMapper.toEntity(requestDto);
        tarefa = service.update(tarefa, tarefaId, casamentoId);
        TarefaResponseDto responseDto = responseMapper.toDomain(tarefa);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TarefaResponseDto> delete(@PathVariable Integer id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
