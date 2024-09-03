package com.bridee.api.service;

import com.bridee.api.dto.request.FornecedorRequestDto;
import com.bridee.api.dto.response.FornecedorResponseDto;
import com.bridee.api.entity.Fornecedor;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.mapper.FornecedorRequestMapper;
import com.bridee.api.mapper.FornecedorResponseMapper;
import com.bridee.api.repository.FornecedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FornecedorService {

    private final FornecedorRepository repository;
    private final FornecedorResponseMapper responseMapper;
    private final FornecedorRequestMapper requestMapper;

    public Page<FornecedorResponseDto> findAll(Pageable pageable){
        return responseMapper.toDomain(repository.findAll(pageable));
    }

    public FornecedorResponseDto findById(Integer id){
        Fornecedor fornecedor = repository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return responseMapper.toDomain(fornecedor);
    }

    public FornecedorResponseDto save(FornecedorRequestDto fornecedorRequestDto){
        Fornecedor fornecedor = requestMapper.toEntity(fornecedorRequestDto);
        return responseMapper.toDomain(repository.save(fornecedor));
    }

    public FornecedorResponseDto update(FornecedorRequestDto fornecedorRequestDto){
        Fornecedor fornecedor = requestMapper.toEntity(fornecedorRequestDto);
        return responseMapper.toDomain(repository.save(fornecedor));
    }

    public void deleteById(Integer id){
        if (!repository.existsById(id)) throw new ResourceNotFoundException();
        repository.deleteById(id);
    }

}
