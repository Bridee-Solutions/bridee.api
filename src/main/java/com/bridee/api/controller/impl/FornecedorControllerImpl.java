package com.bridee.api.controller.impl;

import com.bridee.api.controller.FornecedorController;
import com.bridee.api.dto.request.FornecedorRequestDto;
import com.bridee.api.dto.response.FornecedorResponseDto;
import com.bridee.api.entity.Fornecedor;
import com.bridee.api.mapper.request.FornecedorRequestMapper;
import com.bridee.api.mapper.response.FornecedorResponseMapper;
import com.bridee.api.repository.projection.associado.AssociadoGeralResponseDto;
import com.bridee.api.repository.projection.associado.AssociadoResponseDto;
import com.bridee.api.service.FornecedorService;
import com.bridee.api.utils.UriUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/fornecedores")
@RequiredArgsConstructor
public class FornecedorControllerImpl implements FornecedorController {

    private final FornecedorService fornecedorService;
    private final FornecedorResponseMapper responseMapper;
    private final FornecedorRequestMapper requestMapper;

    @GetMapping
    public ResponseEntity<Page<FornecedorResponseDto>> findAll(Pageable pageable){
        log.info("FORNECEDOR: buscando todas as informações de assessor");
        return ResponseEntity.ok(responseMapper.toDomain(fornecedorService.findAll(pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FornecedorResponseDto> findById(@PathVariable Integer id){
        log.info("FORNECEDOR: buscando as informações do fornecedor de id {}", id);
        return ResponseEntity.ok(responseMapper.toDomain(fornecedorService.findById(id)));
    }

    @GetMapping("/details/categoria/{categoriaId}")
    public ResponseEntity<Page<AssociadoResponseDto>> findFornecedorDetailsByCategoria(@PathVariable Integer categoriaId,
                                                                                       @RequestParam(defaultValue = "") String nome,
                                                                                       Pageable pageable){
        log.info("FORNECEDOR: buscando os detalhes dos fornecedores pela categoria: {}", categoriaId);
        return ResponseEntity.ok(fornecedorService.findFornecedorDetailsByCategoria(categoriaId, nome, pageable));
    }

    @GetMapping("/details/subcategoria/{subcategoriaId}")
    public ResponseEntity<Page<AssociadoResponseDto>> findFornecedorDetails(@PathVariable Integer subcategoriaId, Pageable pageable){
        log.info("FORNECEDOR: buscando os detalhes dos fornecedores pela subcategoria: {}", subcategoriaId);
        return ResponseEntity.ok(fornecedorService.findFornecedorDetails(subcategoriaId, pageable));
    }

    @GetMapping("/information/{id}")
    public ResponseEntity<AssociadoGeralResponseDto> findFornecedorInformation(@PathVariable Integer id){
        log.info("FORNECEDOR: buscando as informações do fornecedor: {}", id);
        return ResponseEntity.ok(fornecedorService.findFornecedorInformations(id));
    }

    @PostMapping
    public ResponseEntity<FornecedorResponseDto> save(@RequestBody @Valid FornecedorRequestDto fornecedorRequestDto){
        log.info("FORNECEDOR: persistindo as informações do fornecedor {}", fornecedorRequestDto.getEmail());
        Fornecedor fornecedor = fornecedorService.save(requestMapper.toEntity(fornecedorRequestDto));
        FornecedorResponseDto responseDto = responseMapper.toDomain(fornecedor);
        return ResponseEntity.created(UriUtils.uriBuilder(responseDto)).body(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FornecedorResponseDto> update(@RequestBody @Valid FornecedorRequestDto fornecedorRequestDto, @PathVariable Integer id){
        log.info("FORNECEDOR: atualizando as informações do fornecedor: {}", id);
        Fornecedor fornecedor = fornecedorService.update(requestMapper.toEntity(fornecedorRequestDto), id);
        FornecedorResponseDto responseDto = responseMapper.toDomain(fornecedor);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        log.info("FORNECEDOR: deletando fornecedor de id: {}", id);
        fornecedorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
