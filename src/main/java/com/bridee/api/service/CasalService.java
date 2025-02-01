package com.bridee.api.service;

import com.bridee.api.entity.Casal;
import com.bridee.api.entity.Casamento;
import com.bridee.api.entity.Role;
import com.bridee.api.entity.UsuarioRole;
import com.bridee.api.entity.enums.RoleEnum;
import com.bridee.api.exception.ResourceAlreadyExists;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.exception.UnprocessableEntityException;
import com.bridee.api.repository.CasalRepository;
import com.bridee.api.repository.CasamentoRepository;
import com.bridee.api.repository.RoleRepository;
import com.bridee.api.repository.UsuarioRoleRepository;
import com.bridee.api.utils.PatchHelper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class CasalService {

    private final CasalRepository repository;
    private final RoleRepository roleRepository;
    private final CasamentoRepository casamentoRepository;
    private final UsuarioRoleRepository usuarioRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final PatchHelper patchHelper;

    public Page<Casal> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public Casal findById(Integer id){
        return repository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public Casal save(Casal casal){
        if (repository.existsByEmail(casal.getEmail())) throw new ResourceAlreadyExists("Email já cadastrado");
        Role role = roleRepository.findByNome(RoleEnum.ROLE_CASAL).orElseThrow(() -> new ResourceNotFoundException("Role não encontrada"));
        casal.setSenha(passwordEncoder.encode(casal.getSenha()));

        if (casal.getExterno()){
            throw new UnprocessableEntityException("Não é possível cadastrar um usuário externo");
        }

        Casal casalCreated = repository.save(casal);
        UsuarioRole usuarioRole = new UsuarioRole(null, role, casalCreated);
        usuarioRoleRepository.save(usuarioRole);
        emailService.sendRegistrationEmail(casalCreated);

        return casalCreated;
    }

    public Casal saveExternal(Casal casal){
        if (repository.existsByEmail(casal.getEmail())) throw new ResourceAlreadyExists("Email já cadastrado");
        Role role = roleRepository.findByNome(RoleEnum.ROLE_CASAL).orElseThrow(() -> new ResourceNotFoundException("Role não encontrada"));
        casal.setEnabled(true);

        if (!casal.getExterno()){
            throw new UnprocessableEntityException("Não é possível cadastrar um usuário que não seja de uma aplicação terceira.");
        }

        Casal casalCreated = repository.save(casal);
        UsuarioRole usuarioRole = new UsuarioRole(null, role, casalCreated);
        usuarioRoleRepository.save(usuarioRole);

        return casalCreated;
    }

    public Casal update(Casal casal, Integer id){
        Casal casalToBeUpdated = repository.findById(id).orElseThrow(ResourceNotFoundException::new);
        patchHelper.mergeNonNull(casal, casalToBeUpdated);
        return repository.save(casalToBeUpdated);
    }

    public void deleteById(Integer id){
        if (!repository.existsById(id)) throw new ResourceNotFoundException();
        repository.deleteById(id);
    }

    public void existsByEmail(String email){
        if (!repository.existsByEmail(email)){
            throw new ResourceNotFoundException("Casal não cadastrado!");
        }
    }

    public Casal updateOrcamentoTotal(Integer id, BigDecimal orcamentoTotal) {
        Casamento casamento = casamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Casamento não encontrado!"));
        Casal casal = casamento.getCasal();
        casal.setOrcamentoTotal(orcamentoTotal);
        return repository.save(casal);
    }
}
