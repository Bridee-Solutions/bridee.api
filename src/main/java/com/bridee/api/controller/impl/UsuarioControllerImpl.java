package com.bridee.api.controller.impl;

import com.bridee.api.controller.UsuarioController;
import com.bridee.api.dto.response.UsuarioResponseDto;
import com.bridee.api.entity.Usuario;
import com.bridee.api.entity.VerificationToken;
import com.bridee.api.mapper.response.UsuarioResponseMapper;
import com.bridee.api.service.EmailService;
import com.bridee.api.service.UsuarioService;
import com.bridee.api.service.VerificationTokenService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Objects;


@Slf4j
@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioControllerImpl implements UsuarioController {

    @Value("${registration.success.redirectUri}")
    private String successRegistrationRedirectUri;
    @Value("${registration.fail.redirectUri}")
    private String failRegistrationRedirectUri;
    private final EmailService emailService;
    private final UsuarioResponseMapper responseMapper;
    private final UsuarioService usuarioService;
    private final VerificationTokenService verificationTokenService;

    @GetMapping("/{email}")
    public ResponseEntity<UsuarioResponseDto> findByEmail(@PathVariable String email){
        log.info("USUARIO: buscando usuário pelo email {}", email);
        Usuario usuario = usuarioService.findByEmail(email);
        return ResponseEntity.ok(responseMapper.toDomain(usuario));
    }

    @GetMapping("/ativar-conta")
    public ResponseEntity<Void> activeAccount(@RequestParam String token, HttpServletResponse response) throws IOException {
        log.info("USUARIO: iniciando processo de ativação de conta");
        HttpStatus httpStatus = HttpStatus.OK;
        VerificationToken verificationToken = verificationTokenService.findVerificationTokenByValor(token);
        Usuario usuario = verificationToken.getUsuario();

        try {
            usuarioService.validateUser(verificationToken);
            log.info("USUARIO: conta ativada com sucesso");
            response.sendRedirect(successRegistrationRedirectUri);
        }catch (IllegalArgumentException e){
            httpStatus = HttpStatus.BAD_REQUEST;
            log.error("USUARIO: não foi possível ativar a conta, token inválido");
            response.sendRedirect("%s#%s".formatted(failRegistrationRedirectUri, Objects.nonNull(usuario) ? usuario.getEmail() : ""));
        }catch (Exception e){
            log.info("USUARIO: não foi possível ativar a conta, erro {}", e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return ResponseEntity.status(httpStatus).build();
    }

    @PostMapping("/resend/verification-email/{email}")
    public ResponseEntity<Void> resendVerificationEmail(@PathVariable String email){
        log.info("USUARIO: reenviando email de confirmação de usuário");
        emailService.sendRegistrationEmail(email);
        return ResponseEntity.noContent().build();
    }

}
