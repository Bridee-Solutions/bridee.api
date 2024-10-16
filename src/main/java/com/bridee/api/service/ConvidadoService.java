package com.bridee.api.service;

import com.bridee.api.entity.Convidado;
import com.bridee.api.exception.ResourceAlreadyExists;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.repository.ConvidadoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class ConvidadoService {

    private final ConvidadoRepository repository;

    public List<Convidado> findAll() {
        return repository.findAll();
    }

    public Convidado findById(Integer id) {
        return repository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public Convidado save(Convidado convidado, Integer conviteId) {
        validateConvidadoConvite(convidado, conviteId);
        return repository.save(convidado);
    }

    public Convidado update(Convidado convidado, Integer id){
        if (!repository.existsById(id)){
            throw new ResourceNotFoundException("Convidado não encontrado!");
        }
        convidado.setId(id);
        return repository.save(convidado);
    }

    public List<Convidado> saveAll(List<Convidado> convidados){
        convidados = removeDuplicatedConvidados(convidados);
        return repository.saveAll(convidados);
    }

    public void deleteById(Integer id) {
        if (!repository.existsById(id)){
            throw new ResourceNotFoundException("Convidado não encontrado!");
        }
        repository.deleteById(id);
    }

    private List<Convidado> removeDuplicatedConvidados(List<Convidado> convidados){
        List<String> telefones = new ArrayList<>();
        List<Convidado> convidadoList = new ArrayList<>();
        convidados.forEach(convidado -> {
            if (!telefones.contains(convidado.getTelefone())){
                telefones.add(convidado.getTelefone());
                convidadoList.add(convidado);
            }
        });
        return convidadoList;
    }

    private void validateConvidadoConvite(Convidado convidado, Integer conviteId){
        if (repository.existsByTelefoneAndConviteId(convidado.getTelefone(), conviteId)){
            throw new ResourceAlreadyExists("Convidado já cadastrado para esse casamento!");
        }
    }
}
