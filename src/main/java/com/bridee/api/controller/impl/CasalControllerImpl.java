package com.bridee.api.controller.impl;

import com.bridee.api.controller.CasalController;
import com.bridee.api.dto.request.CasalRequestDto;
import com.bridee.api.dto.request.ImageMetadata;
import com.bridee.api.dto.request.OrcamentoTotalRequestDto;
import com.bridee.api.dto.request.externo.CasalExternoRequestDto;
import com.bridee.api.dto.response.CasalResponseDto;
import com.bridee.api.dto.response.externo.CasalExternoResponseDto;
import com.bridee.api.entity.Casal;
import com.bridee.api.mapper.request.CasalRequestMapper;
import com.bridee.api.mapper.request.externo.CasalExternoRequestMapper;
import com.bridee.api.mapper.response.CasalResponseMapper;
import com.bridee.api.mapper.response.externo.CasalExternoResponseMapper;
import com.bridee.api.pattern.strategy.blobstorage.BlobStorageStrategy;
import com.bridee.api.pattern.strategy.blobstorage.impl.AzureBlobStorageImpl;
import com.bridee.api.service.CasalService;
import com.bridee.api.service.CasamentoService;
import com.bridee.api.service.ImagemCasalService;
import com.bridee.api.utils.PatchHelper;
import com.bridee.api.utils.UriUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.json.JsonMergePatch;
import java.math.BigDecimal;

@RestController
@RequestMapping("/casais")
@RequiredArgsConstructor
public class CasalControllerImpl implements CasalController {

    private final CasalService service;
    private final CasalRequestMapper requestMapper;
    private final CasalResponseMapper responseMapper;
    private final CasalExternoRequestMapper externoRequestMapper;
    private final CasalExternoResponseMapper externoResponseMapper;
    private final CasamentoService casamentoService;
    private final ImagemCasalService imagemCasalService;
    private final PatchHelper patchHelper;

    @GetMapping
    public ResponseEntity<Page<CasalResponseDto>> findAll(Pageable pageable){
        Page<CasalResponseDto> responseDto = responseMapper.toDomain(service.findAll(pageable));
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CasalResponseDto> findById(@PathVariable Integer id){
        Casal casal = service.findById(id);
        return ResponseEntity.ok(responseMapper.toDomain(casal));
    }

    @PostMapping
    public ResponseEntity<CasalResponseDto> save(@RequestBody @Valid CasalRequestDto requestDto){
        Casal casal = requestMapper.toEntity(requestDto);
        casal = service.save(casal);
        casamentoService.save(casal, requestDto.getQuantidadeConvidados(), requestDto.getDataCasamento(),
                requestDto.isLocalReservado(), requestDto.getLocal(), requestDto.getTamanhoCasamento());
        CasalResponseDto responseDto = responseMapper.toDomain(casal);
        return ResponseEntity.created(UriUtils.uriBuilder(responseDto.getId())).body(responseDto);
    }

    @PostMapping("/externo")
    public ResponseEntity<CasalExternoResponseDto> saveExterno(@RequestBody @Valid CasalExternoRequestDto requestDto){
        Casal casal = externoRequestMapper.toEntity(requestDto);
        casal = service.saveExternal(casal);
        casamentoService.save(casal, requestDto.getQuantidadeConvidados(), requestDto.getDataCasamento(),
                requestDto.isLocalReservado(), requestDto.getLocal(), requestDto.getTamanhoCasamento());
        CasalExternoResponseDto responseDto = externoResponseMapper.toDomain(casal);
        return ResponseEntity.created(UriUtils.uriBuilder(responseDto.getId())).body(responseDto);
    }

    @PostMapping(value = "/imagem-perfil/{casamentoId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadImage(@PathVariable Integer casamentoId,
                                            @RequestParam(value = "metadata") String metadataJson,
                                            @RequestPart("file") MultipartFile file) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ImageMetadata imageMetadata = objectMapper.readValue(metadataJson, ImageMetadata.class);
        imagemCasalService.uploadCasalImage(casamentoId, imageMetadata, file);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CasalResponseDto> update(@RequestBody JsonMergePatch jsonMergePatch, @PathVariable Integer id){
//        Casal casal = requestMapper.toEntity(requestDto);
        Casal casal = patchHelper.mergePatch(jsonMergePatch, new Casal(), Casal.class);
        CasalResponseDto responseDto = responseMapper.toDomain(service.update(casal, id));
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/orcamento-total/{id}")
    public ResponseEntity<CasalResponseDto> updateOrcamentoTotal(@PathVariable Integer id, @RequestBody @Valid OrcamentoTotalRequestDto orcamentoTotal){
        Casal casal = service.updateOrcamentoTotal(id, orcamentoTotal.getOrcamentoTotal());
        return ResponseEntity.ok(responseMapper.toDomain(casal));
    }

}
