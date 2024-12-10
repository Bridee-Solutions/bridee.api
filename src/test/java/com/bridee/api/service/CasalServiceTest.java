package com.bridee.api.service;

import com.bridee.api.entity.Casal;
import com.bridee.api.entity.Casamento;
import com.bridee.api.entity.Role;
import com.bridee.api.entity.UsuarioRole;
import com.bridee.api.entity.enums.RoleEnum;
import com.bridee.api.repository.CasalRepository;
import com.bridee.api.repository.CasamentoRepository;
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

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CasalServiceTest {

    @InjectMocks
    private CasalService casalService;

    @Mock
    private CasalRepository repository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private CasamentoRepository casamentoRepository;
    @Mock
    private UsuarioRoleRepository usuarioRoleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private EmailService emailService;

    @BeforeEach
    void setUp(){
        casalService = new CasalService(repository, roleRepository, casamentoRepository,
                usuarioRoleRepository, passwordEncoder, emailService);
    }

    @Test
    @DisplayName("Listar todos os casais paginados")
    void findAllShouldReturnCasaisPaged(){
        var casal = Mockito.mock(Casal.class);
        Pageable pageable = PageUtilsTest.buildPageable(0, 10);
        when(repository.findAll(pageable)).thenReturn(PageUtilsTest.buildPageImpl(casal));

        Page<Casal> all = casalService.findAll(pageable);
        Casal casalFounded = all.getContent().get(0);

        assertFalse(all.getContent().isEmpty());
        assertEquals(casalFounded, casal);
        assertEquals(casalFounded.getNome(), casal.getNome());
    }

    @Test
    @DisplayName("Update deve atualizar orcamento total do casal")
    void updateOrcamentoTotalShouldIncreaseValue(){
        BigDecimal value = new BigDecimal("400.0");
        var casal = new Casal(1);
        var casamento = Casamento.builder()
                .id(ThreadLocalRandom.current().nextInt(5))
                .casal(casal)
                .build();
        when(casamentoRepository.findById(any())).thenReturn(Optional.of(casamento));
        when(repository.save(any())).thenReturn(casal);

        Casal savedCasal = casalService.updateOrcamentoTotal(casal.getId(), value);
        assertNotNull(savedCasal);
        assertNotNull(savedCasal.getId());
        assertEquals(value, savedCasal.getOrcamentoTotal());
    }

    @Test
    @DisplayName("Deletar deve remover o elemento.")
    void deleteShouldRemoveElementWhenIdIsValid(){

        doNothing().when(repository).deleteById(any());
        when(repository.existsById(any())).thenReturn(true);

        casalService.deleteById(1);

        verify(repository, times(1)).deleteById(1);
        verify(repository, times(1)).existsById(1);
    }

    @Test
    @DisplayName("findById deve retornar o elemento")
    void findByIdShouldReturnElementWhenIdIsValid(){

        var casal = mock(Casal.class);
        when(repository.findById(any())).thenReturn(Optional.of(casal));

        Casal casalFounded = casalService.findById(1);

        assertNotNull(casalFounded);
        verify(repository, times(1)).findById(1);
    }

    @Test
    @DisplayName("update deve atualizar o elemento")
    void updateShouldUpdateElementWhenIdIsValid(){

        var casal = new Casal(1);
        casal.setNome("Ian");
        casal.setOrcamentoTotal(new BigDecimal("300.0"));
        when(repository.save(any())).thenReturn(casal);
        when(repository.existsById(any())).thenReturn(true);

        Casal casalUpdated = casalService.update(casal, 1);

        assertNotNull(casalUpdated);
        assertEquals(casalUpdated.getOrcamentoTotal(), new BigDecimal("300.0"));
        verify(repository, times(1)).existsById(1);
        verify(repository, times(1)).save(casal);
    }

    @Test
    @DisplayName("save deve salvar o casal")
    void saveShouldCreateCasal(){
        var casal = mock(Casal.class);
        RoleEnum casalRole = RoleEnum.ROLE_CASAL;
        Role role = new Role(1, casalRole);
        UsuarioRole usuarioRole = new UsuarioRole(1, role, casal);

        when(repository.existsByEmail(any())).thenReturn(false);
        when(roleRepository.findByNome(any())).thenReturn(Optional.of(role));
        when(usuarioRoleRepository.save(any())).thenReturn(usuarioRole);
        doNothing().when(emailService).sendRegistrationEmail(any());
        when(repository.save(any())).thenReturn(casal);

        Casal casalCreated = casalService.save(casal);

        assertNotNull(casalCreated);
        assertNotNull(casalCreated.getId());
        assertFalse(casalCreated.getExterno());
        verify(repository, times(1)).existsByEmail(any());
        verify(roleRepository, times(1)).findByNome(any());
        verify(usuarioRoleRepository, times(1)).save(any());
        verify(emailService, times(1)).sendRegistrationEmail(any());
    }

    @Test
    @DisplayName("saveExterno deve salvar casal externo")
    void saveExternalShouldCreateCasalExterno(){
        var casal = new Casal(1);
        casal.setExterno(true);
        RoleEnum casalRole = RoleEnum.ROLE_CASAL;
        Role role = new Role(1, casalRole);
        UsuarioRole usuarioRole = new UsuarioRole(1, role, casal);

        when(repository.existsByEmail(any())).thenReturn(false);
        when(roleRepository.findByNome(any())).thenReturn(Optional.of(role));
        when(usuarioRoleRepository.save(any())).thenReturn(usuarioRole);
        doNothing().when(emailService).sendRegistrationEmail(any());
        when(repository.save(any())).thenReturn(casal);

        Casal casalCreated = casalService.saveExternal(casal);

        assertNotNull(casalCreated);
        assertNotNull(casalCreated.getId());
        assertTrue(casalCreated.getExterno());
        verify(repository, times(1)).existsByEmail(any());
        verify(roleRepository, times(1)).findByNome(any());
        verify(usuarioRoleRepository, times(1)).save(any());

    }

}
