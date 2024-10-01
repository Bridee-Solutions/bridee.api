package com.bridee.api.controller;

import com.bridee.api.entity.Usuario;
import com.bridee.api.entity.VerificationToken;
import com.bridee.api.mapper.response.UsuarioResponseMapper;
import com.bridee.api.service.UsuarioService;
import com.bridee.api.service.VerificationTokenService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bridee.api.dto.response.UsuarioResponseDto;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.Objects;


@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    @Value("${registration.success.redirectUri}")
    private String successRegistrationRedirectUri;
    @Value("${registration.fail.redirectUri}")
    private String failRegistrationRedirectUri;
    private final UsuarioResponseMapper responseMapper;
    private final UsuarioService usuarioService;
    private final VerificationTokenService verificationTokenService;

    @GetMapping("/{email}")
    public ResponseEntity<UsuarioResponseDto> findByEmail(@PathVariable String email){
        Usuario usuario = usuarioService.findByEmail(email);
        return ResponseEntity.ok(responseMapper.toDomain(usuario));
    }

    @GetMapping("/ativar-conta")
    public ResponseEntity<Void> activeAccount(@RequestParam String token, HttpServletResponse response) throws IOException {

        HttpStatus httpStatus = HttpStatus.OK;
        VerificationToken verificationToken = verificationTokenService.findVerificationTokenByValor(token);
        Usuario usuario = Objects.nonNull(verificationToken.getUsuario()) ? verificationToken.getUsuario() : null;

        try {
            usuarioService.validateUser(verificationToken);
            response.sendRedirect(successRegistrationRedirectUri);
        }catch (Exception e){
            if (e.getMessage().equals("Token de verificação inválido")){
                httpStatus = HttpStatus.BAD_REQUEST;
                response.sendRedirect("%s#%s".formatted(failRegistrationRedirectUri, Objects.nonNull(usuario) ? usuario.getEmail() : ""));
            }
        }

        return ResponseEntity.status(httpStatus).build();
    }

    @PostMapping("/resend/verification-email")
    public ResponseEntity<String> resendVerificationEmaill(@RequestBody String email){
        Usuario usuario = usuarioService.findByEmail(email);
        usuarioService.sendRegistrationEmail(usuario);
        return ResponseEntity.ok(usuarioService.sendRegistrationEmail(usuario));
    }

}
