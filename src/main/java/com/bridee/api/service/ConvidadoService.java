package com.bridee.api.service;

import com.bridee.api.entity.Convidado;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.repository.ConvidadoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ConvidadoService {

    @Autowired
    private ConvidadoRepository repository;

//    @Autowired
//    private ConvidadoMapper mapper;

    public List<Convidado> findAll() {
        return repository.findAll();
    }

    public Convidado findById(Integer id) {
        return repository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public Convidado save(Convidado guest) {
        return repository.save(guest);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
