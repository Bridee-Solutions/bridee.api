package com.bridee.api.service;

import com.bridee.api.configuration.cache.CacheConstants;
import com.bridee.api.entity.CategoriaConvidado;
import com.bridee.api.repository.CategoriaConvidadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaConvidadoService {

    private final CategoriaConvidadoRepository repository;

    @Cacheable(cacheNames = CacheConstants.CATEGORIA_CONVIDADO)
    public List<CategoriaConvidado> findAll(){
        return repository.findAll();
    }

}
