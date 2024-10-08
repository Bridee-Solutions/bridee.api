package com.bridee.api.controller;

import com.bridee.api.entity.Convidado;
import com.bridee.api.service.ConvidadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/convidados")
@RequiredArgsConstructor
public class ConvidadoController {

    private final ConvidadoService service;

    @GetMapping
    public ResponseEntity<List<Convidado>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Convidado> findById(@PathVariable Integer id) {
        Convidado convidado = service.findById(id);
        return ResponseEntity.ok(convidado);
    }

    @PostMapping
    public ResponseEntity<Convidado> create(@RequestBody Convidado convidado) {
        return ResponseEntity.ok(service.save(convidado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Convidado> update(@PathVariable Integer id, @RequestBody Convidado convidadoDetails) {

        Convidado existingConvidado = service.findById(id);

        existingConvidado.setNome(convidadoDetails.getNome());
        existingConvidado.setCategoria(convidadoDetails.getCategoria());
        existingConvidado.setTelefone(convidadoDetails.getTelefone());
        existingConvidado.setStatus(convidadoDetails.getStatus());

        return ResponseEntity.ok(service.save(existingConvidado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
