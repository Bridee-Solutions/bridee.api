package com.bridee.api.service;

import com.bridee.api.entity.Custo;
import com.bridee.api.exception.ResourceAlreadyExists;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.repository.CustoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CustoService {

    private final CustoRepository custoRepository;

    @Transactional
    public Custo save(Custo custo){
        validateCusto(custo);
        return custoRepository.save(custo);
    }

    @Transactional
    private void validateCusto(Custo custo){
        Integer itemOrcamentoId = Objects.nonNull(custo.getItemOrcamento()) ? custo.getItemOrcamento().getId() : null;
        if (itemOrcamentoId == null){
            throw new IllegalArgumentException("Custo não vinculado a um item!");
        }
        if (custoRepository.existsByNomeAndItemOrcamentoId(custo.getNome(), itemOrcamentoId)){
            throw new ResourceAlreadyExists("Custo já cadastro ao item orçamento!");
        }
    }

    @Transactional
    public List<Custo> saveAll(List<Custo> custos){
        return custoRepository.saveAll(custos);
    }

    @Transactional
    public List<Custo> findAllByItemOrcamentoId(Integer itemOrcamento){
        return custoRepository.findAllByItemOrcamentoId(itemOrcamento);
    }

    public void deleteAllByIds(List<Integer> custosToBeDeleted) {
        custoRepository.deleteAllById(custosToBeDeleted);
    }

    public void deleteById(Integer id) {
        if (!custoRepository.existsById(id)){
            throw new ResourceNotFoundException("Curso não encontrado!");
        }
        custoRepository.deleteById(id);
    }
}
