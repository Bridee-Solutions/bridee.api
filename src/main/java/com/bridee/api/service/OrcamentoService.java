package com.bridee.api.service;

import com.bridee.api.dto.request.OrcamentoCasalRequestDto;
import com.bridee.api.dto.request.SolicitacaoOrcamentoRequestDto;
import com.bridee.api.dto.response.CasalOrcamentoResponseDto;
import com.bridee.api.entity.Casal;
import com.bridee.api.entity.Custo;
import com.bridee.api.entity.ItemOrcamento;
import com.bridee.api.entity.OrcamentoFornecedor;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.mapper.request.FornecedorOrcamentoRequestMapper;
import com.bridee.api.mapper.request.ItemOrcamentoRequestMapper;
import com.bridee.api.repository.AssessorRepository;
import com.bridee.api.repository.CasalRepository;
import com.bridee.api.repository.OrcamentoFornecedorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OrcamentoService {

    private final AssessorRepository assessorRepository;
    private final CasalRepository casalRepository;
    private final ItemOrcamentoRequestMapper itemOrcamentoRequestMapper;
    private final ItemOrcamentoService itemOrcamentoService;
    private final CustoService custoService;
    private final FornecedorOrcamentoRequestMapper orcamentoRequestMapper;
    private final OrcamentoFornecedorRepository repository;

    @Transactional
    public Casal saveOrcamentoCasal(OrcamentoCasalRequestDto orcamentoCasalRequestDto){

        Casal casal = casalRepository.findById(orcamentoCasalRequestDto.getCasalId()).orElseThrow(() -> new ResourceNotFoundException("Casal não encontrado!"));
        List<ItemOrcamento> itens = itemOrcamentoRequestMapper.toEntity(orcamentoCasalRequestDto.getItemOrcamentos());
        List<OrcamentoFornecedor> orcamentoFornecedores = orcamentoRequestMapper.toEntity(orcamentoCasalRequestDto.getOrcamentoFornecedores());

        repository.saveAll(orcamentoFornecedores);
        casal.setOrcamentoFornecedores(orcamentoFornecedores);
        casal.setItemOrcamentos(saveAllItemOrcamento(itens));
        return casalRepository.save(casal);
    }

    private List<ItemOrcamento> saveAllItemOrcamento(List<ItemOrcamento> itensOrcamento){
        verifyAllItensOrcamento(itensOrcamento);
        return itemOrcamentoService.saveAll(itensOrcamento);
    }


    private void verifyAllItensOrcamento(List<ItemOrcamento> itensOrcamento){
        itensOrcamento.forEach(this::addNewItemOrcamento);
    }

    private void addNewItemOrcamento(ItemOrcamento item){
        List<Custo> custosItem = item.getCustos();
        if (Objects.isNull(item.getId())){
            item = itemOrcamentoService.save(item);
        }
        ItemOrcamento finalItem = item;
        custosItem.forEach(custo -> custo.setItemOrcamento(finalItem));
        item.setCustos(saveAllCustosItem(custosItem));
    }

    private List<Custo> saveAllCustosItem(List<Custo> custos){
        verifyAllCustoItem(custos);
        return custoService.saveAll(custos);
    }

    private void verifyAllCustoItem(List<Custo> custos){
        custos.forEach(this::addNewCusto);
    }

    private void addNewCusto(Custo custo){
        if (Objects.isNull(custo.getId())){
            custo = custoService.save(custo);
        }
    }

    public void validateSolicitacaoOrcamento(SolicitacaoOrcamentoRequestDto requestDto){

        String emailAssessorEmpresa = requestDto.getEmailEmpresaAssessor();
        if (!assessorRepository.existsByEmailEmpresa(emailAssessorEmpresa)){
            throw new ResourceNotFoundException("Não existe assessor com esse e-mail");
        }

        String emailCasal = requestDto.getEmailCasal();
        if (!casalRepository.existsByEmail(emailCasal)){
            throw new ResourceNotFoundException("Não existe casal com esse e-mail");
        }
    }
}
