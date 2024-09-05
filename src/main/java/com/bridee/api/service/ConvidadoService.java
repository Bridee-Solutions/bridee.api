package com.bridee.api.service;

import com.bridee.api.mapper.ConvidadoMapper;
import com.bridee.api.model.Convidado;
import com.bridee.api.repository.ConvidadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConvidadoService {

    @Autowired
    private ConvidadoRepository repository;

    @Autowired
    private ConvidadoMapper mapper;

    public List<Convidado> findAll() {
        return mapper.toDomain(repository.findAll());
    }

    public Optional<Convidado> findById(Integer id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    public Convidado save(Convidado guest) {
        return mapper.toDomain(repository.save(mapper.toEntity(guest)));
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
