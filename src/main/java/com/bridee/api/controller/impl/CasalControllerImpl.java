package com.bridee.api.controller.impl;

import com.bridee.api.aop.CoupleIdentifier;
import com.bridee.api.aop.WeddingIdentifier;
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
import com.bridee.api.service.CasalService;
import com.bridee.api.service.CasamentoService;
import com.bridee.api.service.ImagemCasalService;
import com.bridee.api.utils.JsonConverter;
import com.bridee.api.utils.PatchHelper;
import com.bridee.api.utils.UriUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.json.JsonMergePatch;

@RestController
@RequestMapping("/casais")
@RequiredArgsConstructor
@Slf4j
public class CasalControllerImpl implements CasalController {

    private final CasalService service;
    private final CasalRequestMapper requestMapper;
    private final CasalResponseMapper responseMapper;
    private final CasalExternoRequestMapper externoRequestMapper;
    private final CasalExternoResponseMapper externoResponseMapper;
    private final CasamentoService casamentoService;
    private final ImagemCasalService imagemCasalService;
    private final PatchHelper patchHelper;
    private final JsonConverter jsonConverter;

    @GetMapping
    public ResponseEntity<Page<CasalResponseDto>> findAll(Pageable pageable){
        log.info("CASAL: buscando todos os casais.");
        Page<CasalResponseDto> responseDto = responseMapper.toDomain(service.findAll(pageable));
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CasalResponseDto> findById(@PathVariable Integer id){
        log.info("CASAL: buscando o casal de id: {}", id);
        Casal casal = service.findById(id);
        return ResponseEntity.ok(responseMapper.toDomain(casal));
    }

    @PostMapping
    public ResponseEntity<CasalResponseDto> save(@RequestBody @Valid CasalRequestDto requestDto){
        log.info("CASAL: persistindo o casal de email: {}", requestDto.getEmail());
        Casal casal = requestMapper.toEntity(requestDto);
        casal = service.save(casal);
        casamentoService.save(casal, requestDto.getQuantidadeConvidados(), requestDto.getDataCasamento(),
                requestDto.isLocalReservado(), requestDto.getLocal(), requestDto.getTamanhoCasamento());
        CasalResponseDto responseDto = responseMapper.toDomain(casal);
        return ResponseEntity.created(UriUtils.uriBuilder(responseDto.getId())).body(responseDto);
    }

    @PostMapping("/externo")
    public ResponseEntity<CasalExternoResponseDto> saveExterno(@RequestBody @Valid CasalExternoRequestDto requestDto){
        log.info("CASAL: persistindo o casal externo de email: {}", requestDto.getEmail());
        Casal casal = externoRequestMapper.toEntity(requestDto);
        casal = service.saveExternal(casal);
        casamentoService.save(casal, requestDto.getQuantidadeConvidados(), requestDto.getDataCasamento(),
                requestDto.isLocalReservado(), requestDto.getLocal(), requestDto.getTamanhoCasamento());
        CasalExternoResponseDto responseDto = externoResponseMapper.toDomain(casal);
        return ResponseEntity.created(UriUtils.uriBuilder(responseDto.getId())).body(responseDto);
    }

    @PostMapping(value = "/imagem-perfil", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadImage(@RequestParam(value = "metadata") String metadataJson,
                                            @RequestPart("file") MultipartFile file){

        ImageMetadata imageMetadata = jsonConverter.fromJson(metadataJson, ImageMetadata.class);
        imagemCasalService.uploadCasalImage(imageMetadata, file);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(consumes = "application/merge-patch+json")
    public ResponseEntity<CasalResponseDto> update(@RequestBody JsonMergePatch jsonMergePatch,
                                                   @CoupleIdentifier Integer id){
        log.info("CASAL: atualizando informações do casal de id: {}", id);
        Casal casal = patchHelper.mergePatch(jsonMergePatch, new Casal(), Casal.class);
        CasalResponseDto responseDto = responseMapper.toDomain(service.update(casal, id));
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/orcamento-total")
    public ResponseEntity<CasalResponseDto> updateOrcamentoTotal(@WeddingIdentifier Integer id,
                                                                 @RequestBody @Valid OrcamentoTotalRequestDto orcamentoTotal){
        log.info("CASAL: atualizando orcamento do casal de id: {} com o valor {}", id, orcamentoTotal.getOrcamentoTotal());
        Casal casal = service.updateOrcamentoTotal(id, orcamentoTotal.getOrcamentoTotal());
        return ResponseEntity.ok(responseMapper.toDomain(casal));
    }

}
