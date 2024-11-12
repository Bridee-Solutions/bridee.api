package com.bridee.api.controller.impl;

import com.bridee.api.service.CasamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/casamentos")
@RequiredArgsConstructor
public class CasamentoControllerImpl {

    private final CasamentoService casamentoService;

    @PutMapping("/{casamentoId}")
    public ResponseEntity<Void> updateCasamentoMessage(@PathVariable Integer casamentoId,
                                                       @RequestBody String message){
        casamentoService.updateMessage(casamentoId, message);
        return ResponseEntity.ok().build();
    }

}
