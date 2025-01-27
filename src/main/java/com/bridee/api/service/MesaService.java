package com.bridee.api.service;

import com.bridee.api.entity.Casamento;
import com.bridee.api.entity.Mesa;
import com.bridee.api.exception.ResourceAlreadyExists;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.repository.MesaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MesaService {

    private final MesaRepository repository;
    private final CasamentoService casamentoService;

    @Transactional(readOnly = true)
    public List<Mesa> findAllByCasamentoId(Integer casamentoId){
        casamentoService.existsById(casamentoId);
        return repository.findAllByCasamentoId(casamentoId);
    }

    @Transactional(readOnly = true)
    public Mesa findById(Integer id){
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Mesa não encontrada!"));
    }

    public Mesa save(Mesa mesa, Integer casamentoId){
        validateMesaCasamento(mesa, casamentoId);
        mesa.setCasamento(Casamento.builder()
                .id(casamentoId)
                .build());
        return repository.save(mesa);
    }

    public Mesa update(Mesa mesa, Integer id){
        if (!repository.existsById(id)){
            throw new ResourceNotFoundException("Mesa não encontrada");
        }
        mesa.setId(id);
        return repository.save(mesa);
    }

    public void deleteById(Integer id){
        if (!repository.existsById(id)){
            throw new ResourceNotFoundException("Mesa não encontrada");
        }
        repository.deleteById(id);
    }

    private void validateMesaCasamento(Mesa mesa, Integer casamentoId){
        if (repository.existsByNomeAndCasamentoId(mesa.getNome(), casamentoId)){
            throw new ResourceAlreadyExists("Mesa já cadastrada para esse casamento.");
        }
    }

}
