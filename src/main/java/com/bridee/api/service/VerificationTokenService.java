package com.bridee.api.service;

import com.bridee.api.entity.Usuario;
import com.bridee.api.entity.VerificationToken;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.repository.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VerificationTokenService {

    private final VerificationTokenRepository repository;

    public VerificationToken generateVerificationToken(Usuario usuario){
        VerificationToken verificationToken = VerificationToken.builder()
                .valor(UUID.randomUUID().toString())
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .confirmedAt(null)
                .usuario(usuario)
                .build();
        return repository.save(verificationToken);
    }

    private boolean isVerificationTokenValid(VerificationToken verificationToken){
            if (LocalDateTime.now().isAfter(verificationToken.getExpiresAt())
                    || verificationToken.getConfirmedAt() != null){
                return false;
            }
            return true;
    }

    public VerificationToken findVerificationTokenByValor(String valor){
        return repository.findByValor(valor).orElseThrow(() -> new ResourceNotFoundException("Token de verificação não encontrado."));
    }

    public void confirmVerificationToken(VerificationToken verificationToken){
        if (!isVerificationTokenValid(verificationToken)){
            throw new IllegalArgumentException("Token de verificação inválido");
        }
        verificationToken.setConfirmedAt(LocalDateTime.now());
        repository.save(verificationToken);
    }

}
