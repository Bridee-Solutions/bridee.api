package com.bridee.api.service;

import com.bridee.api.entity.Casamento;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.repository.CasamentoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CasamentoService {

    private final CasamentoRepository repository;

    public Casamento findById(Integer id){
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Casamento não encontrado!"));
    }

}
