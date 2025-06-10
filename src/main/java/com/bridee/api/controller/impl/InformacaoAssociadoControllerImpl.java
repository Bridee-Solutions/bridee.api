package com.bridee.api.controller.impl;

import com.bridee.api.aop.AdvisorIdentifier;
import com.bridee.api.dto.request.InformacaoAssociadoDto;
import com.bridee.api.dto.request.InformacaoAssociadoPerfilDto;
import com.bridee.api.dto.response.InformacaoAssociadoResponseDto;
import com.bridee.api.entity.InformacaoAssociado;
import com.bridee.api.mapper.response.InformacaoAssociadoResponseMapper;
import com.bridee.api.service.InformacaoAssociadoService;
import com.bridee.api.utils.JsonConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@Slf4j
@RestController
@RequestMapping("/informacao-associados")
@RequiredArgsConstructor
public class InformacaoAssociadoControllerImpl {
    
    private final InformacaoAssociadoService service;
    private final InformacaoAssociadoResponseMapper responseMapper;
    private final JsonConverter jsonConverter;

    
    @PostMapping(value = "/perfil/{assessorId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<InformacaoAssociadoResponseDto> save(@AdvisorIdentifier Integer assessorId,
                                                               @RequestParam("json") String informacaoAssociadoDto,
                                                               @RequestPart("imagemPrincipal") MultipartFile imagemPrincipal,
                                                               @RequestPart("imagemSecundaria") MultipartFile imagemSecundaria,
                                                               @RequestPart("imagemTerciaria") MultipartFile imagemTerciaria,
                                                               @RequestPart("imagemQuaternaria") MultipartFile imagemQuaternaria,
                                                               @RequestPart("imagemQuinaria") MultipartFile imagemQuinaria) throws JsonProcessingException {

        InformacaoAssociadoDto informacaoAssociado = jsonConverter
                .fromJson(informacaoAssociadoDto, InformacaoAssociadoDto.class);
        log.info("INFORMAÇÃO ASSOCIADO: criando informação associado para o assessor de id {}", assessorId);
        InformacaoAssociadoPerfilDto informacaoPerfil = new InformacaoAssociadoPerfilDto(informacaoAssociado, imagemPrincipal,
                imagemSecundaria, imagemTerciaria, imagemQuaternaria, imagemQuinaria);
        InformacaoAssociado info = service.save(informacaoPerfil, assessorId);
        return ResponseEntity.ok(responseMapper.toDomain(info));
    }

    @PostMapping(value = "/{fornecedorId}/perfil-fornecedor", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<InformacaoAssociadoResponseDto> saveFornecedorInformation(@PathVariable Integer fornecedorId,
                                                               @RequestParam("json") String informacaoAssociadoDto,
                                                               @RequestPart("imagemPrincipal") MultipartFile imagemPrincipal,
                                                               @RequestPart("imagemSecundaria") MultipartFile imagemSecundaria,
                                                               @RequestPart("imagemTerciaria") MultipartFile imagemTerciaria,
                                                               @RequestPart("imagemQuaternaria") MultipartFile imagemQuaternaria,
                                                               @RequestPart("imagemQuinaria") MultipartFile imagemQuinaria) throws JsonProcessingException {

        InformacaoAssociadoDto informacaoAssociado = jsonConverter.fromJson(informacaoAssociadoDto, InformacaoAssociadoDto.class);
        log.info("INFORMAÇÃO ASSOCIADO: criando informação associado para o fornecedor de id {}", fornecedorId);
        InformacaoAssociadoPerfilDto informacaoPerfil = new InformacaoAssociadoPerfilDto(informacaoAssociado, imagemPrincipal,
                imagemSecundaria, imagemTerciaria, imagemQuaternaria, imagemQuinaria);
        InformacaoAssociado info = service.saveFornecedor(informacaoPerfil, fornecedorId);
        return ResponseEntity.ok(responseMapper.toDomain(info));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InformacaoAssociadoResponseDto> findByAssessorId(@PathVariable Integer id){
        log.info("INFORMAÇÃO ASSOCIADO: buscando informação associado de id {}", id);
        InformacaoAssociado info = service.findByAssessorId(id);
        InformacaoAssociadoResponseDto response = responseMapper.toDomain(info);
        response.setDetalhes(service.buildInformacaoAssociadoDetalhes(info));
        return ResponseEntity.ok().body(response);
    }
}
