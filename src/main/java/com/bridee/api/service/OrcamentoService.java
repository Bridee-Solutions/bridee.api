package com.bridee.api.service;

import com.bridee.api.dto.request.OrcamentoCasalRequestDto;
import com.bridee.api.dto.request.SolicitacaoOrcamentoRequestDto;
import com.bridee.api.entity.Casal;
import com.bridee.api.entity.Custo;
import com.bridee.api.entity.ItemOrcamento;
import com.bridee.api.entity.OrcamentoFornecedor;
import com.bridee.api.exception.CsvDownloadErro;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.mapper.request.FornecedorOrcamentoRequestMapper;
import com.bridee.api.mapper.request.ItemOrcamentoRequestMapper;
import com.bridee.api.projection.orcamento.OrcamentoProjection;
import com.bridee.api.repository.AssessorRepository;
import com.bridee.api.repository.CasalRepository;
import com.bridee.api.utils.CsvUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class OrcamentoService {

    private final AssessorRepository assessorRepository;
    private final CasalRepository casalRepository;
    private final ItemOrcamentoRequestMapper itemOrcamentoRequestMapper;
    private final ItemOrcamentoService itemOrcamentoService;
    private final CustoService custoService;
    private final FornecedorOrcamentoRequestMapper orcamentoRequestMapper;
    private final OrcamentoFornecedorService orcamentoFornecedorService;
    private final SubcategoriaServicoService subcategoriaServicoService;

    @Transactional
    public OrcamentoProjection findCasalOrcamento(Integer casalId){
        if (!casalRepository.existsById(casalId)){
            throw new ResourceNotFoundException("Casal não encontrado!");
        }
        orcamentoFornecedorService.findByCasalId(casalId);
        subcategoriaServicoService.findAll();
        itemOrcamentoService.findAllByCasalId(casalId);
        return casalRepository.findOrcamentoByCasalId(casalId);
    }

    @Transactional
    public Casal saveOrcamentoCasal(OrcamentoCasalRequestDto orcamentoCasalRequestDto){

        Casal casal = casalRepository.findById(orcamentoCasalRequestDto.getCasalId()).orElseThrow(() -> new ResourceNotFoundException("Casal não encontrado!"));
        List<ItemOrcamento> itens = itemOrcamentoRequestMapper.toEntity(orcamentoCasalRequestDto.getItemOrcamentos());
        List<OrcamentoFornecedor> orcamentoFornecedores = orcamentoRequestMapper.toEntity(orcamentoCasalRequestDto.getOrcamentoFornecedores());

        orcamentoFornecedorService.saveAll(orcamentoFornecedores);
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

    @Transactional
    public BigDecimal calculateTotalOrcamento(Casal casal){

        if (Objects.isNull(casal)){
            throw new ResourceNotFoundException("Não foi possível calcular o orcamento de um casal não cadastrado");
        }
        List<ItemOrcamento> itensOrcamento = casal.getItemOrcamentos();
        List<OrcamentoFornecedor> orcamentoFornecedores = casal.getOrcamentoFornecedores();

        Double valorTotalItens = itensOrcamento.stream().mapToDouble(item -> item.getCustos().stream()
                .mapToDouble(custo -> Double.parseDouble(custo.getPrecoAtual().toString())).sum()).sum();

        Double valorTotalFornecedores = orcamentoFornecedores.stream()
                .mapToDouble(orcamento -> Double.parseDouble(orcamento.getPreco().toString())).sum();

        BigDecimal totalItens = new BigDecimal(valorTotalItens);
        BigDecimal totalFornecedores = new BigDecimal(valorTotalFornecedores);
        return totalItens.add(totalFornecedores);
    }

    public byte[] generateCsvFile(Integer id) {

        OrcamentoProjection orcamentoProjection = findCasalOrcamento(id);

        byte[] csv = null;
        try {
            csv = CsvUtils.createResumeCostsCsv(orcamentoProjection);
        } catch (IOException e) {
            throw new CsvDownloadErro("Não foi possível gerar o arquivo csv para o casal %d".formatted(id));
        }

        return csv;
    }
}
