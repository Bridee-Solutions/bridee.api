package com.bridee.api.controller.impl;

import com.bridee.api.dto.request.AtividadeRequestDto;
import com.bridee.api.dto.response.AtividadeResponseDto;
import com.bridee.api.entity.Atividade;
import com.bridee.api.mapper.request.AtividadeRequestMapper;
import com.bridee.api.mapper.response.AtividadeResponseMapper;
import com.bridee.api.service.CronogramaService;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/atividades")
@RequiredArgsConstructor
public class AtividadeControllerImpl {

    private final CronogramaService cronogramaService;
    private final AtividadeResponseMapper responseMapper;
    private final AtividadeRequestMapper requestMapper;

    @GetMapping("/{casamentoId}")
    public ResponseEntity<List<AtividadeResponseDto>> findAllAtividadesByCasamentoId(@PathVariable Integer casamentoId){
        List<Atividade> atividades = cronogramaService.findAllByCasamentoId(casamentoId);
        List<AtividadeResponseDto> responseDto = responseMapper.toDomain(atividades);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/cronograma/{cronogramaId}")
    public ResponseEntity<AtividadeResponseDto> saveAtividade(@RequestBody @Valid AtividadeRequestDto requestDto,
                                                              @PathVariable Integer cronogramaId){
        Atividade atividade = requestMapper.toEntity(requestDto);
        atividade = cronogramaService.saveAtividade(atividade, cronogramaId);
        AtividadeResponseDto responseDto = responseMapper.toDomain(atividade);
        return ResponseEntity.created(UriUtils.uriBuilder(responseDto.getId())).body(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AtividadeResponseDto> updateAtividade(@RequestBody @Valid AtividadeRequestDto requestDto,
                                                                @PathVariable Integer id){
        Atividade atividade = requestMapper.toEntity(requestDto);
        atividade = cronogramaService.updateAtividade(atividade, id);
        AtividadeResponseDto responseDto = responseMapper.toDomain(atividade);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAtividade(@PathVariable Integer id){
        cronogramaService.deleteAtividadeById(id);
        return ResponseEntity.noContent().build();
    }

}
