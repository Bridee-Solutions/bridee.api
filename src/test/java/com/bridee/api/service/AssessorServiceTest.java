package com.bridee.api.service;

import com.bridee.api.dto.request.ValidateAssessorFieldsRequestDto;
import com.bridee.api.dto.response.ValidateAssessorFieldsResponseDto;
import com.bridee.api.entity.Assessor;
import com.bridee.api.mapper.response.AssociadoGeralResponseMapper;
import com.bridee.api.mapper.response.AssociadoGeralResponseMapperImpl;
import com.bridee.api.projection.associado.AssociadoResponseDto;
import com.bridee.api.projection.associado.AssociadoResponseProjection;
import com.bridee.api.repository.AssessorRepository;
import com.bridee.api.repository.RoleRepository;
import com.bridee.api.repository.UsuarioRoleRepository;
import com.bridee.api.util.PageUtilsTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class AssessorServiceTest {

    @InjectMocks
    private AssessorService assessorService;

    @Mock
    private AssessorRepository assessorRepository;
    @Mock
    private UsuarioRoleRepository usuarioRoleRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private EmailService emailService;
    @Mock
    private ImagemService imagemService;
    @Mock
    private TipoCasamentoService tipoCasamentoService;
    @Mock
    private FormaPagamentoService formaPagamentoService;
    @Mock
    private AssociadoGeralResponseMapper geralResponseMapper;
    @Mock
    private InformacaoAssociadoService informacaoAssociadoService;

    @BeforeEach
    void setUp(){
        assessorService = new AssessorService(assessorRepository, usuarioRoleRepository, roleRepository,
                passwordEncoder, emailService, imagemService, tipoCasamentoService, formaPagamentoService,
                geralResponseMapper, informacaoAssociadoService);
        geralResponseMapper = new AssociadoGeralResponseMapperImpl();
    }

    @Test
    @DisplayName("Lista todos os assessores paginados")
    void findAllShouldListAllAssessoresPaged(){
        var assessor = Mockito.mock(Assessor.class);
        Pageable pageable = PageUtilsTest.buildPageable(0, 10);
        Mockito.when(assessorRepository.findAll(pageable)).thenReturn(PageUtilsTest.buildPageImpl(assessor));

        Page<Assessor> all = assessorService.findAll(pageable);
        Assessor assessorFounded = all.getContent().get(0);

        Assertions.assertFalse(all.getContent().isEmpty());
        Assertions.assertEquals(assessorFounded, assessor);
        Assertions.assertEquals(assessorFounded.getNome(), assessor.getNome());
    }

    @Test
    @DisplayName("Listar todos os usuários por nome")
    void findAllByNomeShouldReturnAssessoresFiltered(){
        var assessor = new Assessor(1);
        assessor.setNome("Ian");
        Pageable pageable = PageUtilsTest.buildPageable(0, 10);
        Mockito.when(assessorRepository.findAllByNome(Mockito.any(), Mockito.any())).thenReturn(PageUtilsTest.buildPageImpl(assessor));

        Page<Assessor> all = assessorService.findAllByNome("Ian",pageable);

        Assertions.assertFalse(all.getContent().isEmpty());
        Assertions.assertEquals(all.getContent().get(0), assessor);
    }

    @Test
    @DisplayName("Lista os assessores e seus detalhes")
    void findAssessorDetailsShouldReturnAssessoresPaged(){
        var mockito = Mockito.mock(AssociadoResponseProjection.class);

        Pageable page = PageUtilsTest.buildPageable(0, 10);
        Page<AssociadoResponseProjection> associadoResponse = PageUtilsTest.buildPageImpl(mockito);

        Mockito.when(assessorRepository.findAssessorDetails(page)).thenReturn(associadoResponse);
        Page<AssociadoResponseDto> responseDto = assessorService.findAssessoresDetails(page);

        Assertions.assertNotNull(responseDto);
    }

    @Test
    @DisplayName("Validar informações assessor")
    void validateShouldReturnTrueWhenInformationInvalid(){

        var request = Mockito.mock(ValidateAssessorFieldsRequestDto.class);

        Mockito.when(assessorRepository.existsByCnpj(Mockito.any())).thenReturn(true);
        Mockito.when(assessorRepository.existsByEmailEmpresa(Mockito.any())).thenReturn(true);

        ValidateAssessorFieldsResponseDto fields = assessorService.validateAssessorFields(request);

        Assertions.assertNotNull(fields);
        Assertions.assertTrue(fields.getCnpjEmpresaExists());
        Assertions.assertTrue(fields.getEmailEmpresaExists());

        Assertions.assertNotNull(responseDto);
    }

//    @Test
//    @DisplayName("Validar informações assessor")
//    void validateShouldReturnTrueWhenInformationInvalid(){
//
//        Mockito.when(assessorRepository.existsByCnpj(Mockito.any())).thenReturn()
//
//    }

}
