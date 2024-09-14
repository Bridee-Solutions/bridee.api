package com.bridee.api.service;

import com.bridee.api.entity.Assessor;
import com.bridee.api.entity.Role;
import com.bridee.api.entity.UsuarioRole;
import com.bridee.api.entity.enums.RoleEnum;
import com.bridee.api.exception.ResourceAlreadyExists;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.repository.AssessorRepository;
import com.bridee.api.repository.RoleRepository;
import com.bridee.api.repository.UsuarioRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssessorService {

    private final AssessorRepository assessorRepository;
    private final UsuarioRoleRepository usuarioRoleRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public Page<Assessor> findAll(Pageable pageable){
         return assessorRepository.findAll(pageable);
    }

    public Assessor save(Assessor assessor){
        if (assessorRepository.existsByCnpjOrEmail(assessor.getCnpj(), assessor.getEmail())) throw new ResourceAlreadyExists("Assessor já cadastrado");
        List<Role> roles = roleRepository.findAll();
        if (roles.isEmpty()) throw new ResourceNotFoundException("Nenhuma role foi encontrada");
        assessor.setSenha(passwordEncoder.encode(assessor.getSenha()));
        Assessor createdAssessor = assessorRepository.save(assessor);
        roles.stream().filter(role -> role.getNome().equals(RoleEnum.ROLE_USER) || role.getNome().equals(RoleEnum.ROLE_ASSESSOR))
                .forEach(userRole ->
                        usuarioRoleRepository.save(new UsuarioRole(null, userRole, createdAssessor)));
        return createdAssessor;
    }

    public Assessor findById(Integer id){
        return assessorRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public Assessor update(Assessor assessor, Integer id){
        if (!assessorRepository.existsById(id)) throw new ResourceNotFoundException();
        assessor.setId(id);
        return assessorRepository.save(assessor);
    }

    public void deleteById(Integer id){
        if (!assessorRepository.existsById(id)) throw new ResourceNotFoundException();
    }
}
