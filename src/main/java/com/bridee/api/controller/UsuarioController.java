package com.bridee.api.controller;

import com.bridee.api.entity.Usuario;
import com.bridee.api.mapper.response.UsuarioResponseMapper;
import com.bridee.api.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bridee.api.dto.response.UsuarioResponseDto;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioResponseMapper responseMapper;
    private final UsuarioService usuarioService;

    @GetMapping("/{email}")
    public ResponseEntity<UsuarioResponseDto> findByEmail(@PathVariable String email){
        Usuario usuario = usuarioService.findByEmail(email);
        return ResponseEntity.ok(responseMapper.toDomain(usuario));
    }

}
