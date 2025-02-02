package com.bridee.api.service;

import com.bridee.api.configuration.cache.CacheConstants;
import com.bridee.api.entity.CategoriaServico;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.repository.CategoriaServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaServicoService {

    private final CategoriaServicoRepository repository;

    @Cacheable(cacheNames = CacheConstants.CATEGORIA_SERVICO)
    public Page<CategoriaServico> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public void existsById(Integer id){
        if (!repository.existsById(id)){
            throw new ResourceNotFoundException("CategoriaServico não encontrado!");
        }
    }

}
