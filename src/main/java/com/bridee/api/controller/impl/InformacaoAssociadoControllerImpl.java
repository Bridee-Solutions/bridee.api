package com.bridee.api.controller.impl;

import com.bridee.api.dto.model.InformacaoAssociadoDto;
import com.bridee.api.entity.InformacaoAssociado;
import com.bridee.api.mapper.request.InformacaoAssociadoMapper;
import com.bridee.api.service.FormaPagamentoAssociadoService;
import com.bridee.api.service.InformacaoAssociadoService;
import com.bridee.api.service.TipoCasamentoAssociadoService;
import com.bridee.api.service.TipoCerimoniaAssociadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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
    
    @PutMapping("/{id}/perfil")
    public ResponseEntity<Void> update(@PathVariable Integer id,
    @RequestBody @Valid InformacaoAssociadoDto informacaoDto
    ){
        InformacaoAssociado info = service.update(requestMapper.toEntity(informacaoDto.getInformacaoAssociado()), id);
        
        tipoCasamentoAssociadoService.update(informacaoDto.getTiposCasamento(), info);
        tipoCerimoniaAssociadoService.update(informacaoDto.getTiposCerimonia(), info);
        formaPagamentoAssociadoService.update(informacaoDto.getFormasPagamento(), info);
        
        return ResponseEntity.noContent().build();
    }
}
