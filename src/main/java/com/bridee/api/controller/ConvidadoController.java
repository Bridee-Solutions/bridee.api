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
        Optional<Convidado> convidado = service.findById(id);
        return convidado.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Convidado> create(@RequestBody Convidado convidado) {
        return ResponseEntity.ok(service.save(convidado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Convidado> update(@PathVariable Integer id, @RequestBody Convidado convidadoDetails) {
        Optional<Convidado> existingConvidadoOpt = service.findById(id);

        if (existingConvidadoOpt.isPresent()) {
            Convidado existingConvidado = existingConvidadoOpt.get();

            existingConvidado.setNome(convidadoDetails.getNome());
            existingConvidado.setCategoria(convidadoDetails.getCategoria());
            existingConvidado.setTelefone(convidadoDetails.getTelefone());
            existingConvidado.setStatus(convidadoDetails.getStatus());

            Convidado updatedConvidado = service.save(existingConvidado);
            return ResponseEntity.ok(updatedConvidado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
