package com.bridee.api.controller;

import com.bridee.api.entity.Acompanhante;
import com.bridee.api.service.AcompanhanteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/acompanhantes")
public class AcompanhanteController {

    private final AcompanhanteService service;

    public AcompanhanteController(AcompanhanteService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Acompanhante>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Acompanhante> findById(@PathVariable Integer id) {
        Optional<Acompanhante> acompanhante = service.findById(id);
        return acompanhante.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Acompanhante> save(@RequestBody Acompanhante acompanhante){
        return ResponseEntity.ok(service.save(acompanhante));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Acompanhante> update(@PathVariable Integer id, @RequestBody Acompanhante acompanhanteDetails) {
        Optional<Acompanhante> existingAcompanhanteOpt = service.findById(id);

        if (existingAcompanhanteOpt.isPresent()) {
            Acompanhante existingAcompanhante = existingAcompanhanteOpt.get();

            existingAcompanhante.setNome(acompanhanteDetails.getNome());
            existingAcompanhante.setStatus(acompanhanteDetails.getStatus());

            Acompanhante updatedAcompanhante = service.save(existingAcompanhante);
            return ResponseEntity.ok(updatedAcompanhante);
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
