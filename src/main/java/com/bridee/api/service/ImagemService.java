package com.bridee.api.service;

import com.bridee.api.entity.Imagem;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.repository.FornecedorRepository;
import com.bridee.api.repository.ImagemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImagemService {

    private final ImagemRepository repository;
    private final FornecedorRepository fornecedorRepository;

    public List<String> findUrlImagensFornecedor(Integer fornecedorId){
        return findImagensFornecedor(fornecedorId).stream()
                .map(Imagem::getUrl).toList();
    }

    private List<Imagem> findImagensFornecedor(Integer fornecedorId){
        if (!fornecedorRepository.existsById(fornecedorId)){
            throw new ResourceNotFoundException("Fornecedor não encontrado!");
        }
        return repository.findByFornecedorId(fornecedorId);
    }

}
