package com.bridee.api.service;

import com.bridee.api.configuration.cache.CacheConstants;
import com.bridee.api.entity.SubcategoriaServico;
import com.bridee.api.repository.SubcategoriaServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubcategoriaServicoServiceCacheAssistent {

    private final SubcategoriaServicoRepository repository;

    @Cacheable(cacheNames = CacheConstants.SUBCATEGORIA_SERVICO, keyGenerator = "customKeyGenerator")
    List<SubcategoriaServico> findAll(){
        return repository.findAllSubcategories();
    }
}
