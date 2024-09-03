package com.bridee.api.controller;

import com.bridee.api.dto.request.FornecedorRequestDto;
import com.bridee.api.dto.response.FornecedorResponseDto;
import com.bridee.api.service.FornecedorService;
import com.bridee.api.utils.UriUtils;
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
@RequestMapping("/fornecedores")
@RequiredArgsConstructor
public class FornecedorController {

    private final FornecedorService fornecedorService;

    @GetMapping
    public ResponseEntity<Page<FornecedorResponseDto>> findAll(Pageable pageable){
        return ResponseEntity.ok(fornecedorService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FornecedorResponseDto> findById(@PathVariable Integer id){
        return ResponseEntity.ok(fornecedorService.findById(id));
    }

    @PostMapping
    public ResponseEntity<FornecedorResponseDto> save(@RequestBody FornecedorRequestDto fornecedorRequestDto){
        FornecedorResponseDto fornecedorResponseDto = fornecedorService.save(fornecedorRequestDto);
        return ResponseEntity.created(UriUtils.uriBuilder(fornecedorResponseDto)).body(fornecedorResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FornecedorResponseDto> update(@RequestBody FornecedorRequestDto fornecedorRequestDto, @PathVariable Integer id){
        return ResponseEntity.ok(fornecedorService.update(fornecedorRequestDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        fornecedorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
