package com.bridee.api.controller;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bridee.api.utils.UriUtils;

import com.bridee.api.dto.request.UsuarioRequestDto;
import com.bridee.api.dto.response.UsuarioResponseDto;
import com.bridee.api.entity.Usuario;
import com.bridee.api.service.UsuarioService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UsuarioController {
    
//    private final UsuarioService usuarioService;
//
//    // @PostMapping("/login")
//    // public ResponseEntity<UsuarioResponseDto> login(@RequestBody String email, @RequestBody String senha) {
//
//    // }
//
//    @PostMapping("/register")
//    public ResponseEntity<UsuarioResponseDto> register(@RequestBody UsuarioRequestDto user) {
//        UsuarioResponseDto userResponseDto = this.usuarioService.resgister(user);
//
//        return ResponseEntity.created(UriUtils.uriBuilder(userResponseDto)).body(userResponseDto);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<UsuarioResponseDto> updateUser(@RequestBody UsuarioRequestDto user, @PathVariable Integer id) {
//        return ResponseEntity.ok(this.usuarioService.update(user, id));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
//        this.deleteUser(id);
//        return ResponseEntity.notFound().build();
//    }
}
