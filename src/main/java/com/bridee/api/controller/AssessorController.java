package com.bridee.api.controller;

import com.bridee.api.dto.request.AssessorRequestDto;
import com.bridee.api.dto.request.SolicitacaoOrcamentoRequestDto;
import com.bridee.api.dto.request.ValidateAssessorFieldsRequestDto;
import com.bridee.api.dto.request.externo.AssessorExternoRequestDto;
import com.bridee.api.dto.response.AssessorResponseDto;
import com.bridee.api.dto.response.ValidateAssessorFieldsResponseDto;
import com.bridee.api.dto.response.externo.AssessorExternoResponseDto;
import com.bridee.api.entity.Assessor;
import com.bridee.api.mapper.request.AssessorRequestMapper;
import com.bridee.api.mapper.request.externo.AssessorExternoRequestMapper;
import com.bridee.api.mapper.response.AssessorResponseMapper;
import com.bridee.api.mapper.response.externo.AssessorExternoResponseMapper;
import com.bridee.api.service.AssessorService;
import com.bridee.api.service.EmailService;
import com.bridee.api.utils.UriUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/assessores")
@RequiredArgsConstructor
public class AssessorController {

    private final AssessorService service;
    private final EmailService emailService;
    private final AssessorRequestMapper requestMapper;
    private final AssessorResponseMapper responseMapper;
    private final AssessorExternoRequestMapper externoRequestMapper;
    private final AssessorExternoResponseMapper externoResponseMapper;

    @GetMapping
    public ResponseEntity<Page<AssessorResponseDto>> findAll(Pageable pageable){
        return ResponseEntity.ok(responseMapper.toDomain(service.findAll(pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssessorResponseDto> findById(@PathVariable Integer id){
        return ResponseEntity.ok(responseMapper.toDomain(service.findById(id)));
    }

    @PostMapping("/solicitar-orcamento")
    public ResponseEntity<Void> sendOrcamentoEmail(@RequestBody @Valid SolicitacaoOrcamentoRequestDto requestDto){
        emailService.sendOrcamentoEmail(requestDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<AssessorResponseDto> save(@RequestBody @Valid AssessorRequestDto requestDto){
        Assessor assessor = service.save(requestMapper.toEntity(requestDto));
        AssessorResponseDto responseDto = responseMapper.toDomain(assessor);
        return ResponseEntity.created(UriUtils.uriBuilder(responseDto.getId())).body(responseDto);
    }

    @PostMapping("/externo")
    public ResponseEntity<AssessorExternoResponseDto> saveExternal(@RequestBody @Valid AssessorExternoRequestDto requestDto){
        Assessor assessor = service.saveExternal(externoRequestMapper.toEntity(requestDto));
        AssessorExternoResponseDto responseDto = externoResponseMapper.toDomain(assessor);
        return ResponseEntity.created(UriUtils.uriBuilder(responseDto.getId())).body(responseDto);

    }

    @PostMapping("/validate-fields")
    public ResponseEntity<ValidateAssessorFieldsResponseDto> validateFields(@RequestBody @Valid ValidateAssessorFieldsRequestDto requestDto){
        ValidateAssessorFieldsResponseDto responseDto = service.validateAssessorFields(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssessorResponseDto> update(@RequestBody @Valid AssessorRequestDto requestDto, @PathVariable Integer id){
        Assessor assessor = service.update(requestMapper.toEntity(requestDto), id);
        return ResponseEntity.ok(responseMapper.toDomain(assessor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
