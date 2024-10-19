package com.bridee.api.service;

import com.bridee.api.entity.ItemOrcamento;
import com.bridee.api.exception.ResourceAlreadyExists;
import com.bridee.api.repository.ItemOrcamentoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemOrcamentoService {

    private final ItemOrcamentoRepository repository;

    @Transactional
    public ItemOrcamento save(ItemOrcamento itemOrcamento){
        Integer casalId = itemOrcamento.getCasal().getId();
        String tipo = itemOrcamento.getTipo();
        if (repository.existsByTipoAndCasalId(tipo, casalId)){
            throw new ResourceAlreadyExists("Item orçamento com tipo %s já existe para o casal de id %d".formatted(tipo, casalId));
        }
        return repository.save(itemOrcamento);
    }

    @Transactional
    public List<ItemOrcamento> saveAll(List<ItemOrcamento> itemOrcamentos){
        return repository.saveAll(itemOrcamentos);
    }

}
