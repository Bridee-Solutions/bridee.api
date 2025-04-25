package com.bridee.api.service;

import com.bridee.api.configuration.cache.CacheConstants;
import com.bridee.api.dto.request.ValidateAssessorFieldsRequestDto;
import com.bridee.api.dto.response.ImagemResponseDto;
import com.bridee.api.dto.response.ValidateAssessorFieldsResponseDto;
import com.bridee.api.entity.Assessor;
import com.bridee.api.entity.Role;
import com.bridee.api.entity.UsuarioRole;
import com.bridee.api.entity.enums.RoleEnum;
import com.bridee.api.exception.ResourceAlreadyExists;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.mapper.response.AssociadoGeralResponseMapper;
import com.bridee.api.repository.projection.associado.AssociadoGeralResponseDto;
import com.bridee.api.repository.projection.associado.AssociadoGeralResponseProjection;
import com.bridee.api.repository.projection.associado.AssociadoResponseDto;
import com.bridee.api.repository.projection.associado.AssociadoResponseProjection;
import com.bridee.api.repository.AssessorRepository;
import com.bridee.api.repository.RoleRepository;
import com.bridee.api.repository.UsuarioRoleRepository;
import com.bridee.api.utils.PageUtils;
import com.bridee.api.utils.PatchHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AssessorService {

    private final AssessorRepository assessorRepository;
    private final UsuarioRoleRepository usuarioRoleRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final ImagemService imagemService;
    private final TipoCasamentoService tipoCasamentoService;
    private final FormaPagamentoService formaPagamentoService;
    private final AssociadoGeralResponseMapper geralResponseMapper;
    private final InformacaoAssociadoService informacaoAssociadoService;
    private final PatchHelper patchHelper;

    public Page<Assessor> findAll(Pageable pageable){
         return assessorRepository.findAll(pageable);
    }

    public Page<Assessor> findAllByNome(String nome, Pageable pageable) {
        return assessorRepository.findAllByNome(nome, pageable);
    }

    public Integer getAdviserIdFromEmail(String email){
        return assessorRepository.findIdByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Assessor não encontrado"));
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheConstants.ASSESSOR)
    public Page<AssociadoResponseDto> findAssessoresDetails(Pageable pageable){
        Page<AssociadoResponseProjection> assessorDetails = assessorRepository.findAssessorDetails(pageable);
        log.info("ASSESSOR: convertendo AssociadoResponseProjection para AssociadoResponseDto");
        List<AssociadoResponseDto> associadoResponse = geralResponseMapper.toResponseDto(assessorDetails.getContent());

        fillAssessorDetailsImages(associadoResponse);
        return PageUtils.collectionToPage(associadoResponse, assessorDetails);
    }

    private void fillAssessorDetailsImages(List<AssociadoResponseDto> associadoResponse){
        log.info("ASSESSOR: iniciando busca das imagens dos assessores");
        associadoResponse.forEach(associado -> {
            log.info("ASSESSOR: buscando imagem principal para o assessor com id: {}", associado.getId());
            ImagemResponseDto imagemPrincipal = informacaoAssociadoService.findImagemPrincipal(associado.getInformacaoAssociadoId());
            if (Objects.nonNull(imagemPrincipal)){
                log.info("ASSESSOR: imagem encontrada com sucesso!");
                associado.setImagemPrincipal(imagemPrincipal.getData());
            }
        });
    }

    @Transactional(readOnly = true)
    public AssociadoGeralResponseDto findAssessorInformation(Integer assessorId){
        if (!assessorRepository.existsById(assessorId)){
            throw new ResourceNotFoundException("Assessor não encontrado!");
        }

        AssociadoGeralResponseProjection resultProjection = assessorRepository.findAssessorInformations(assessorId);
        if (Objects.isNull(resultProjection)){
            throw new ResourceNotFoundException("Não foi possível recuperar as informações do assessor");
        }

        return buildAssociadoGeralResponseDto(resultProjection);
    }

    private AssociadoGeralResponseDto buildAssociadoGeralResponseDto(AssociadoGeralResponseProjection result){
        List<String> imagesUrl = imagemService.findBase64UrlImagensAssessor(result.getId());
        List<String> nomeFormasPagamento = formaPagamentoService.findNomeFormasPagamentoAssessor(result.getId());
        List<String> tiposCasamento = tipoCasamentoService.findNomeTiposCasamentoAssessor(result.getId());

        log.info("ASSESSOR: convertendo AssociadoResponseProjection para AssociadoGeralResponseDto");
        AssociadoGeralResponseDto geralDto = geralResponseMapper.toGeralDto(result);
        geralDto.setImagens(imagesUrl);
        geralDto.setFormasPagamento(nomeFormasPagamento);
        geralDto.setTiposCasamento(tiposCasamento);
        return geralDto;
    }

    public Assessor save(Assessor assessor){
        if (assessorRepository.existsByCnpjOrEmail(assessor.getCnpj(), assessor.getEmail())){
            log.error("ASSESSOR: e-mail {} já cadastrado", assessor.getEmail());
            throw new ResourceAlreadyExists("Email já cadastrado");
        }

        log.info("ASSESSOR: buscando a role de assessor");
        Role role = roleRepository.findByNome(RoleEnum.ROLE_ASSESSOR).orElseThrow(() -> new ResourceNotFoundException("Role não encontrada"));
        assessor.setSenha(passwordEncoder.encode(assessor.getSenha()));

        Assessor createdAssessor = assessorRepository.save(assessor);
        UsuarioRole usuarioRole = new UsuarioRole(null, role, createdAssessor);
        usuarioRoleRepository.save(usuarioRole);
        log.info("ASSESSOR: assessor criado com sucesso com id: {}", createdAssessor.getId());
        emailService.sendRegistrationEmail(createdAssessor.getEmail());
        return createdAssessor;
    }

    public Assessor saveExternal(Assessor assessor){
        if (assessorRepository.existsByCnpjOrEmail(assessor.getCnpj(), assessor.getEmail())){
            log.error("ASSESSOR: e-mail {} já cadastrado para externo", assessor.getEmail());
            throw new ResourceAlreadyExists("Email já cadastrado");
        }

        log.info("ASSESSOR: buscando a role de assessor");
        Role role = roleRepository.findByNome(RoleEnum.ROLE_ASSESSOR).orElseThrow(() -> new ResourceNotFoundException("Role não encontrada"));
        assessor.setEnabled(true);

        Assessor createdAssessor = assessorRepository.save(assessor);
        UsuarioRole usuarioRole = new UsuarioRole(null, role, createdAssessor);
        usuarioRoleRepository.save(usuarioRole);
        log.info("ASSESSOR: assessor externo criado com sucesso com id: {}", createdAssessor.getId());
        return createdAssessor;
    }

    @Transactional(readOnly = true)
    public Assessor findById(Integer id){
        return assessorRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public Assessor update(Assessor assessor, Integer id){
        Assessor assessorToBeUpdated = assessorRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        patchHelper.mergeNonNull(assessor, assessorToBeUpdated);
        return assessorRepository.save(assessorToBeUpdated);
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

    public void existsByEmailEmpresa(String emailAssessorEmpresa) {
        if (!assessorRepository.existsByEmailEmpresa(emailAssessorEmpresa)){
            throw new ResourceNotFoundException("Não existe assessor com esse e-mail");
        }
    }
}
