package com.bridee.api.service;

import com.bridee.api.entity.Mesa;
import com.bridee.api.exception.ResourceAlreadyExists;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.repository.MesaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MesaService {

    private final MesaRepository repository;

    public List<Mesa> findAllByCasamentoId(Integer casamentoId){
        //TODO: validar se o casamento existe
        return repository.findAllByCasamentoId(casamentoId);
    }

    public Mesa findById(Integer id){
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Mesa não encontrada!"));
    }

    @Transactional
    public Mesa save(Mesa mesa, Integer casamentoId){
        validateMesaCasamento(mesa, casamentoId);
        return repository.save(mesa);
    }

    @Transactional
    public Mesa update(Mesa mesa, Integer id){
        if (!repository.existsById(id)){
            throw new ResourceNotFoundException("Mesa não encontrada");
        }
        mesa.setId(id);
        return repository.save(mesa);
    }

    @Transactional
    public void deleteById(Integer id){
        if (!repository.existsById(id)){
            throw new ResourceNotFoundException("Mesa não encontrada");
        }
        repository.deleteById(id);
    }

    private void validateMesaCasamento(Mesa mesa, Integer casamentoId){
        if (repository.existsByNomeAndCasamentoId(mesa, casamentoId)){
            throw new ResourceAlreadyExists("Mesa já cadastrada para esse casamento.");
        }
    }

}
