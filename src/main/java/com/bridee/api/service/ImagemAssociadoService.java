package com.bridee.api.service;

import com.bridee.api.entity.ImagemAssociado;
import com.bridee.api.repository.ImagemAssociadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImagemAssociadoService {

    private final ImagemAssociadoRepository repository;

    public List<ImagemAssociado> saveAll(List<ImagemAssociado> imagens){
        return repository.saveAll(imagens);
    }

}
