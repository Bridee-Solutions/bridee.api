package com.bridee.api.services;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bridee.api.dto.request.UsuarioRequestDto;
import com.bridee.api.dto.response.UsuarioResponseDto;
import com.bridee.api.entity.Usuario;
import com.bridee.api.mapper.UsuarioRequestMapper;
import com.bridee.api.mapper.UsuarioResponseMapper;
import com.bridee.api.repository.UsuarioRepository;
import com.bridee.api.exception.ResourceAlreadyExists;
import com.bridee.api.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    
    private final UsuarioRepository repository;
    private final UsuarioResponseMapper responseMapper;
    private final UsuarioRequestMapper requestMapper;
    
    public UsuarioResponseDto resgister(UsuarioRequestDto usuarioRequestDto) {
        Optional<Usuario> userEmail = this.repository.findByEmail(usuarioRequestDto.getEmail());
        if (userEmail.isPresent()) throw new ResourceAlreadyExists();
        Usuario user = this.requestMapper.toEntity(usuarioRequestDto);
        return responseMapper.toDomain(this.repository.save(user));
    }

    public UsuarioResponseDto put(UsuarioRequestDto userRequestDto, Integer id){
        if (!this.repository.existsById(id)) throw new ResourceNotFoundException();

        userRequestDto.setId(id);
        Usuario user = this.requestMapper.toEntity(userRequestDto);
        return this.responseMapper.toDomain(this.repository.save(user));
    }


    public void deleteById(Integer id){
        if (!repository.existsById(id)) throw new ResourceNotFoundException();
        repository.deleteById(id);
    }
}
