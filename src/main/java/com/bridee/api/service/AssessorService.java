package com.bridee.api.service;

import com.bridee.api.dto.request.ValidateAssessorFieldsRequestDto;
import com.bridee.api.dto.response.ValidateAssessorFieldsResponseDto;
import com.bridee.api.entity.Assessor;
import com.bridee.api.entity.Role;
import com.bridee.api.entity.UsuarioRole;
import com.bridee.api.entity.enums.RoleEnum;
import com.bridee.api.exception.ResourceAlreadyExists;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.mapper.response.AssociadoGeralResponseMapper;
import com.bridee.api.projection.fornecedor.AssociadoGeralResponseDto;
import com.bridee.api.projection.fornecedor.AssociadoGeralResponseProjection;
import com.bridee.api.projection.fornecedor.AssociadoResponseProjection;
import com.bridee.api.repository.AssessorRepository;
import com.bridee.api.repository.RoleRepository;
import com.bridee.api.repository.UsuarioRoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class AssessorService {

    private final AssessorRepository assessorRepository;
    private final UsuarioRoleRepository usuarioRoleRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final ImagemService imagemService;
    private final FormaPagamentoService formaPagamentoService;
    private final AssociadoGeralResponseMapper geralResponseMapper;

    public Page<Assessor> findAll(Pageable pageable){
         return assessorRepository.findAll(pageable);
    }

    public Page<AssociadoResponseProjection> findAssessoresDetails(Pageable pageable){
        return assessorRepository.findAssessorDetails(pageable);
    }

    public AssociadoGeralResponseDto findAssessorInformation(Integer assessorId){
        if (!assessorRepository.existsById(assessorId)){
            throw new ResourceNotFoundException("Assessor não encontrado!");
        }

        AssociadoGeralResponseProjection resultProjection = assessorRepository.findFornecedorInformations(assessorId);
        List<String> imagensUrl = imagemService.findUrlImagensAssessor(assessorId);
        List<String> nomeFormasPagamento = formaPagamentoService.findNomeFormasPagamentoAssessor(assessorId);

        AssociadoGeralResponseDto geralResponseDto = geralResponseMapper.toGeralDto(resultProjection);
        geralResponseDto.setImagens(imagensUrl);
        geralResponseDto.setFormasPagamento(nomeFormasPagamento);

        return geralResponseDto;
    }

    public Assessor save(Assessor assessor){
        if (assessorRepository.existsByCnpjOrEmail(assessor.getCnpj(), assessor.getEmail())) throw new ResourceAlreadyExists("Email já cadastrado");
        Role role = roleRepository.findByNome(RoleEnum.ROLE_ASSESSOR).orElseThrow(() -> new ResourceNotFoundException("Role não encontrada"));
        assessor.setSenha(passwordEncoder.encode(assessor.getSenha()));
        Assessor createdAssessor = assessorRepository.save(assessor);
        UsuarioRole usuarioRole = new UsuarioRole(null, role, createdAssessor);
        usuarioRoleRepository.save(usuarioRole);
        emailService.sendRegistrationEmail(createdAssessor);
        return createdAssessor;
    }

    public Assessor saveExternal(Assessor assessor){
        if (assessorRepository.existsByCnpjOrEmail(assessor.getCnpj(), assessor.getEmail())) throw new ResourceAlreadyExists("Email já cadastrado");
        Role role = roleRepository.findByNome(RoleEnum.ROLE_ASSESSOR).orElseThrow(() -> new ResourceNotFoundException("Role não encontrada"));
        assessor.setEnabled(true);
        Assessor createdAssessor = assessorRepository.save(assessor);
        UsuarioRole usuarioRole = new UsuarioRole(null, role, createdAssessor);
        usuarioRoleRepository.save(usuarioRole);
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
        assessorRepository.deleteById(id);
    }

    public ValidateAssessorFieldsResponseDto validateAssessorFields(ValidateAssessorFieldsRequestDto requestDto) {
        boolean existsByCnpj = assessorRepository.existsByCnpj(requestDto.getCnpj());
        boolean existsByEmailEmpresa = assessorRepository.existsByEmailEmpresa(requestDto.getEmailEmpresa());
        return ValidateAssessorFieldsResponseDto.builder()
                .cnpjEmpresaExists(existsByCnpj)
                .emailEmpresaExists(existsByEmailEmpresa)
                .build();
    }

}
