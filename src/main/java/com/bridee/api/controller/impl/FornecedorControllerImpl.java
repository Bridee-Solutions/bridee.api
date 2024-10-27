package com.bridee.api.controller.impl;

import com.bridee.api.controller.FornecedorController;
import com.bridee.api.dto.request.FornecedorRequestDto;
import com.bridee.api.dto.response.FornecedorResponseDto;
import com.bridee.api.entity.Fornecedor;
import com.bridee.api.mapper.request.FornecedorRequestMapper;
import com.bridee.api.mapper.response.FornecedorResponseMapper;
import com.bridee.api.projection.associado.AssociadoGeralResponseDto;
import com.bridee.api.projection.associado.AssociadoResponseProjection;
import com.bridee.api.service.FornecedorService;
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
@RequestMapping("/fornecedores")
@RequiredArgsConstructor
public class FornecedorControllerImpl implements FornecedorController {

    private final FornecedorService fornecedorService;
    private final FornecedorResponseMapper responseMapper;
    private final FornecedorRequestMapper requestMapper;

    @GetMapping
    public ResponseEntity<Page<FornecedorResponseDto>> findAll(Pageable pageable){
        return ResponseEntity.ok(responseMapper.toDomain(fornecedorService.findAll(pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FornecedorResponseDto> findById(@PathVariable Integer id){
        return ResponseEntity.ok(responseMapper.toDomain(fornecedorService.findById(id)));
    }

    @GetMapping("/details/categoria/{categoriaId}")
    public ResponseEntity<Page<AssociadoResponseProjection>> findFornecedorDetailsByCategoria(@PathVariable Integer categoriaId, Pageable pageable){
        return ResponseEntity.ok(fornecedorService.findFornecedorDetailsByCategoria(categoriaId, pageable));
    }

    @GetMapping("/details/subcategoria/{subcategoriaId}")
    public ResponseEntity<Page<AssociadoResponseProjection>> findFornecedorDetails(@PathVariable Integer subcategoriaId, Pageable pageable){
        return ResponseEntity.ok(fornecedorService.findFornecedorDetails(subcategoriaId, pageable));
    }

    @GetMapping("/information/{id}")
    public ResponseEntity<AssociadoGeralResponseDto> findFornecedorInformation(@PathVariable Integer id){
        return ResponseEntity.ok(fornecedorService.findFornecedorInformations(id));
    }

    @PostMapping
    public ResponseEntity<FornecedorResponseDto> save(@RequestBody @Valid FornecedorRequestDto fornecedorRequestDto){
        Fornecedor fornecedor = fornecedorService
                .save(requestMapper.toEntity(fornecedorRequestDto));
        FornecedorResponseDto responseDto = responseMapper
                .toDomain(fornecedor);
        return ResponseEntity.created(UriUtils.uriBuilder(responseDto)).body(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FornecedorResponseDto> update(@RequestBody @Valid FornecedorRequestDto fornecedorRequestDto, @PathVariable Integer id){
        Fornecedor fornecedor = fornecedorService.update(requestMapper.toEntity(fornecedorRequestDto), id);
        FornecedorResponseDto responseDto = responseMapper.toDomain(fornecedor);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        fornecedorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
