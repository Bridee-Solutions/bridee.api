package com.bridee.api.service;

import com.bridee.api.entity.Casal;
import com.bridee.api.entity.Casamento;
import com.bridee.api.repository.CasamentoRepository;
import com.bridee.api.repository.CustoRepository;
import com.bridee.api.repository.OrcamentoFornecedorRepository;
import com.bridee.api.utils.PatchHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class CasamentoServiceTest {

    @InjectMocks
    private CasamentoService casamentoService;

    @Mock
    private CasamentoRepository repository;
    @Mock
    private OrcamentoFornecedorRepository orcamentoFornecedorRepository;
    @Mock
    private CustoRepository custoRepository;
    @Mock
    private PedidoAssessoriaService pedidoAssessoriaService;
    @Mock
    private PatchHelper patchHelper;

    @BeforeEach
    void setUp(){
        casamentoService = new CasamentoService(repository,
                orcamentoFornecedorRepository, custoRepository, pedidoAssessoriaService, patchHelper);
    }

    @Test
    void findByIdShouldReturnCasamento(){
        var casamento = Mockito.mock(Casamento.class);
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(casamento));

        Casamento casal = casamentoService.findById(1);

        Assertions.assertNotNull(casal);
        Assertions.assertNotNull(casal.getId());
    }

    @Test
    void saveCasamentoShouldReturnCasamento(){

        var casamento = Mockito.mock(Casamento.class);
        Casal casalWedding = new Casal(1);
        casamento.setCasal(casalWedding);
        Mockito.when(repository.save(Mockito.any())).thenReturn(casamento);
        Casamento casamentoSaved = casamentoService.save(casalWedding, 250, LocalDate.now(), true, "Rua", "201-300");

        Assertions.assertNotNull(casamentoSaved);
    }

}
