package com.bridee.api.service;

import com.bridee.api.entity.CategoriaServico;
import com.bridee.api.repository.CategoriaServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaServicoService {

    private final CategoriaServicoRepository repository;

    public Page<CategoriaServico> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

}
