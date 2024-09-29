package com.bridee.api.controller;

import com.bridee.api.dto.request.CasalRequestDto;
import com.bridee.api.dto.request.externo.CasalExternoRequestDto;
import com.bridee.api.dto.response.CasalResponseDto;
import com.bridee.api.dto.response.externo.CasalExternoResponseDto;
import com.bridee.api.entity.Casal;
import com.bridee.api.mapper.request.CasalRequestMapper;
import com.bridee.api.mapper.request.externo.CasalExternoRequestMapper;
import com.bridee.api.mapper.response.CasalResponseMapper;
import com.bridee.api.mapper.response.externo.CasalExternoResponseMapper;
import com.bridee.api.service.CasalService;
import com.bridee.api.utils.UriUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/casais")
@RequiredArgsConstructor
public class CasalController {

    private final CasalService service;
    private final CasalRequestMapper requestMapper;
    private final CasalResponseMapper responseMapper;
    private final CasalExternoRequestMapper externoRequestMapper;
    private final CasalExternoResponseMapper externoResponseMapper;

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
    public ResponseEntity<CasalResponseDto> save(@RequestBody CasalRequestDto requestDto){
        Casal casal = requestMapper.toEntity(requestDto);
        CasalResponseDto responseDto = responseMapper.toDomain(service.save(casal));
        return ResponseEntity.created(UriUtils.uriBuilder(responseDto.getId())).body(responseDto);
    }

    @PostMapping("/externo")
    public ResponseEntity<CasalExternoResponseDto> saveExterno(@RequestBody CasalExternoRequestDto requestDto){
        Casal casal = externoRequestMapper.toEntity(requestDto);
        CasalExternoResponseDto responseDto = externoResponseMapper.toDomain(service.saveExternal(casal));
        return ResponseEntity.created(UriUtils.uriBuilder(responseDto.getId())).body(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CasalResponseDto> update(@RequestBody CasalRequestDto requestDto, @PathVariable Integer id){
        Casal casal = requestMapper.toEntity(requestDto);
        CasalResponseDto responseDto = responseMapper.toDomain(service.update(casal, id));
        return ResponseEntity.ok(responseDto);
    }


}
