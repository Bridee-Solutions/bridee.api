package com.bridee.api.service;

import com.bridee.api.entity.Acompanhante;
import com.bridee.api.repository.AcompanhanteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AcompanhanteService {

    @Autowired
    private AcompanhanteRepository repository;

//    @Autowired
//    private AcompanhanteMapper mapper;

    public List<Acompanhante> findAll() {
        return repository.findAll();
    }

    public Optional<Acompanhante> findById(Integer id) {
        return repository.findById(id);
    }

    public Acompanhante save(Acompanhante companion) {
        return repository.save(companion);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
