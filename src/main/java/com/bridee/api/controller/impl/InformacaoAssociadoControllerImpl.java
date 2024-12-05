package com.bridee.api.controller.impl;

import com.bridee.api.dto.model.InformacaoAssociadoDto;
import com.bridee.api.dto.response.InformacaoAssociadoResponseDto;
import com.bridee.api.entity.InformacaoAssociado;
import com.bridee.api.mapper.request.InformacaoAssociadoMapper;
import com.bridee.api.mapper.response.InformacaoAssociadoResponseMapper;
import com.bridee.api.service.FormaPagamentoAssociadoService;
import com.bridee.api.service.InformacaoAssociadoService;
import com.bridee.api.service.TipoCasamentoAssociadoService;
import com.bridee.api.service.TipoCerimoniaAssociadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/informacao-associados")
@RequiredArgsConstructor
public class InformacaoAssociadoControllerImpl {
    
    private final InformacaoAssociadoService service;
    private final TipoCasamentoAssociadoService tipoCasamentoAssociadoService;
    private final TipoCerimoniaAssociadoService tipoCerimoniaAssociadoService;
    private final FormaPagamentoAssociadoService formaPagamentoAssociadoService;
    private final InformacaoAssociadoMapper requestMapper;
    private final InformacaoAssociadoResponseMapper responseMapper;
    
    
    @PostMapping("/{id}/perfil")
    public ResponseEntity<Void> save(@PathVariable Integer id, @RequestBody @Valid InformacaoAssociadoDto informacaoDto){
        InformacaoAssociado informacaoAssociado = requestMapper.toEntity(informacaoDto);
        InformacaoAssociado info = service.save(informacaoAssociado, id);
        tipoCasamentoAssociadoService.update(informacaoDto.getTiposCasamento(), info);
        tipoCerimoniaAssociadoService.update(informacaoDto.getTiposCerimonia(), info);
        formaPagamentoAssociadoService.update(informacaoDto.getFormasPagamento(), info);
        
        return ResponseEntity.noContent().build();
    }



    @GetMapping("/{id}")
    public ResponseEntity<InformacaoAssociadoResponseDto> findByAssessorId(@PathVariable Integer id){
        InformacaoAssociado info = service.findByAssessorId(id);
        InformacaoAssociadoResponseDto response = responseMapper.toDomain(info);
        response.setTiposCasamento(tipoCasamentoAssociadoService.findAllByInformacaoAssociadoId(info));
        response.setTiposCerimonia(tipoCerimoniaAssociadoService.findAllByInformacaoAssociadoId(info));
        response.setFormasPagamento(formaPagamentoAssociadoService.findAllByInformacaoAssociadoId(info));
        return ResponseEntity.ok().body(response);
    }
}
