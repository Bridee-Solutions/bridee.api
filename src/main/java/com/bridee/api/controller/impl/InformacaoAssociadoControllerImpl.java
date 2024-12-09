package com.bridee.api.controller.impl;

import com.bridee.api.dto.request.InformacaoAssociadoDto;
import com.bridee.api.dto.request.InformacaoAssociadoPerfilDto;
import com.bridee.api.dto.response.InformacaoAssociadoResponseDto;
import com.bridee.api.entity.InformacaoAssociado;
import com.bridee.api.mapper.request.InformacaoAssociadoMapper;
import com.bridee.api.mapper.response.InformacaoAssociadoResponseMapper;
import com.bridee.api.service.FormaPagamentoAssociadoService;
import com.bridee.api.service.ImagemAssociadoService;
import com.bridee.api.service.InformacaoAssociadoService;
import com.bridee.api.service.TipoCasamentoAssociadoService;
import com.bridee.api.service.TipoCerimoniaAssociadoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/informacao-associados")
@RequiredArgsConstructor
public class InformacaoAssociadoControllerImpl {
    
    private final InformacaoAssociadoService service;
    private final TipoCasamentoAssociadoService tipoCasamentoAssociadoService;
    private final TipoCerimoniaAssociadoService tipoCerimoniaAssociadoService;
    private final FormaPagamentoAssociadoService formaPagamentoAssociadoService;
    private final ImagemAssociadoService imagemAssociadoService;
    private final InformacaoAssociadoMapper requestMapper;
    private final InformacaoAssociadoResponseMapper responseMapper;
    
    
    @PostMapping(value = "/{assessorId}/perfil", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<InformacaoAssociadoResponseDto> save(@PathVariable Integer assessorId,
                                                               @RequestParam("json") String informacaoAssociadoDto,
                                                               @RequestPart("imagemPrincipal") MultipartFile imagemPrincipal,
                                                               @RequestPart("imagemSecundaria") MultipartFile imagemSecundaria,
                                                               @RequestPart("imagemTerciaria") MultipartFile imagemTerciaria,
                                                               @RequestPart("imagemQuaternaria") MultipartFile imagemQuaternaria,
                                                               @RequestPart("imagemQuinaria") MultipartFile imagemQuinaria) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        InformacaoAssociadoDto informacaoAssociado = objectMapper
                .readValue(informacaoAssociadoDto, InformacaoAssociadoDto.class);
        InformacaoAssociadoPerfilDto informacaoPerfil = new InformacaoAssociadoPerfilDto(informacaoAssociado, imagemPrincipal,
                imagemSecundaria, imagemTerciaria, imagemQuaternaria, imagemQuinaria);
        InformacaoAssociado info = service.save(informacaoPerfil, assessorId);
        return ResponseEntity.ok(responseMapper.toDomain(info));
    }

    @PostMapping(value = "/{fornecedorId}/perfil", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<InformacaoAssociadoResponseDto> saveFornecedorInformation(@PathVariable Integer fornecedorId,
                                                               @RequestParam("json") String informacaoAssociadoDto,
                                                               @RequestPart("imagemPrincipal") MultipartFile imagemPrincipal,
                                                               @RequestPart("imagemSecundaria") MultipartFile imagemSecundaria,
                                                               @RequestPart("imagemTerciaria") MultipartFile imagemTerciaria,
                                                               @RequestPart("imagemQuaternaria") MultipartFile imagemQuaternaria,
                                                               @RequestPart("imagemQuinaria") MultipartFile imagemQuinaria) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        InformacaoAssociadoDto informacaoAssociado = objectMapper
                .readValue(informacaoAssociadoDto, InformacaoAssociadoDto.class);
        InformacaoAssociadoPerfilDto informacaoPerfil = new InformacaoAssociadoPerfilDto(informacaoAssociado, imagemPrincipal,
                imagemSecundaria, imagemTerciaria, imagemQuaternaria, imagemQuinaria);
        InformacaoAssociado info = service.save(informacaoPerfil, fornecedorId);
        return ResponseEntity.ok(responseMapper.toDomain(info));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InformacaoAssociadoResponseDto> findByAssessorId(@PathVariable Integer id){
        InformacaoAssociado info = service.findByAssessorId(id);
        InformacaoAssociadoResponseDto response = responseMapper.toDomain(info);
        response.setTiposCasamento(tipoCasamentoAssociadoService.findAllByInformacaoAssociadoId(info));
        response.setTiposCerimonia(tipoCerimoniaAssociadoService.findAllByInformacaoAssociadoId(info));
        response.setFormasPagamento(formaPagamentoAssociadoService.findAllByInformacaoAssociadoId(info));
        response.setImagemPrimaria(imagemAssociadoService.findImagemPrincipalBase64(info.getId()));
        response.setImagensSecundarias(imagemAssociadoService.findImagensSecundarias(info.getId()));
        return ResponseEntity.ok().body(response);
    }
}
