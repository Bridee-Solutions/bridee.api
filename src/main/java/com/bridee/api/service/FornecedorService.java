package com.bridee.api.service;

import com.bridee.api.entity.Fornecedor;
import com.bridee.api.exception.ResourceAlreadyExists;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.projection.FornecedorResponseProjection;
import com.bridee.api.repository.FornecedorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class FornecedorService {

    private final FornecedorRepository repository;

    public Page<Fornecedor> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public Fornecedor findById(Integer id){
        return repository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public FornecedorResponseProjection findFornecedorDetails(Integer id){
        if (!repository.existsById(id)){
            throw new ResourceNotFoundException("Fornecedor não encontrado!");
        }
        return repository.findFornecedorDetails(id);
    }

    public Fornecedor save(Fornecedor fornecedor){
        Optional<Fornecedor> optionalFornecedor = repository.findByCnpj(fornecedor.getCnpj());
        if(optionalFornecedor.isPresent()) throw new ResourceAlreadyExists("Fornecedor já cadastrado");
        return repository.save(fornecedor);
    }

    public Fornecedor update(Fornecedor fornecedor, Integer id){
        if (!repository.existsById(id)) throw new ResourceNotFoundException();
        fornecedor.setId(id);
        return repository.save(fornecedor);
    }

    public void deleteById(Integer id){
        if (!repository.existsById(id)) throw new ResourceNotFoundException();
        repository.deleteById(id);
    }

}
