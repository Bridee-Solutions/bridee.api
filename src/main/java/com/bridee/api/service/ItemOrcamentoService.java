package com.bridee.api.service;

import com.bridee.api.entity.Custo;
import com.bridee.api.entity.ItemOrcamento;
import com.bridee.api.exception.ResourceAlreadyExists;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.exception.UnprocessableEntityException;
import com.bridee.api.mapper.response.ItemOrcamentoResponseMapper;
import com.bridee.api.repository.projection.orcamento.ItemOrcamentoProjection;
import com.bridee.api.repository.ItemOrcamentoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ItemOrcamentoService {

    private final ItemOrcamentoRepository repository;
    private final CustoService custoService;
    private final ItemOrcamentoResponseMapper responseMapper;

    public List<ItemOrcamentoProjection> findAllByCasalId(Integer casalId){
        return repository.findAllByCasalId(casalId);
    }

    public ItemOrcamento save(ItemOrcamento itemOrcamento){
        Integer casalId = itemOrcamento.getCasal().getId();
        String tipo = itemOrcamento.getTipo();

        log.info("ITEM ORCAMENTO: salvando item orcamento do tipo {} para o casal {}", itemOrcamento.getTipo(), casalId);
        if (repository.existsByTipoAndCasalId(tipo, casalId)){
            log.error("ITEM ORCAMENTO: item orçamento com tipo {} já existe para o casal {}", tipo, casalId);
            throw new ResourceAlreadyExists("Item orçamento com tipo %s já existe para o casal de id %d".formatted(tipo, casalId));
        }

        return repository.save(itemOrcamento);
    }

    public List<ItemOrcamento> saveAll(List<ItemOrcamento> itemOrcamentos){
        return repository.saveAll(itemOrcamentos);
    }


    public List<ItemOrcamento> saveAllItemOrcamento(List<ItemOrcamento> itensOrcamento){
        verifyAllItensOrcamento(itensOrcamento);
        return saveAll(itensOrcamento);
    }


    private void verifyAllItensOrcamento(List<ItemOrcamento> itensOrcamento){
        itensOrcamento.forEach(this::addNewItemOrcamento);
    }

    private void removeInactivesItensOrcamento(List<ItemOrcamento> itensOrcamento){
        if (itensOrcamento.isEmpty()){
            throw new UnprocessableEntityException("Nenhum item orcamento informado");
        }

        List<Integer> itensToBeRemovedIds = itensOrcamentoToBeRemoved(itensOrcamento);
        repository.deleteAllById(itensToBeRemovedIds);
    }

    private List<Integer> itensOrcamentoToBeRemoved(List<ItemOrcamento> itensOrcamento){
        Integer casalId = itensOrcamento.get(0).getCasal().getId();

        List<ItemOrcamento> allItemOrcamento = responseMapper.fromProjection(repository.findAllByCasalId(casalId));
        List<Integer> itemOrcamentoIds = itensOrcamento.stream().map(ItemOrcamento::getId).toList();
        List<Integer> allItemOrcamentoIds = allItemOrcamento.stream().map(ItemOrcamento::getId).toList();

        return allItemOrcamentoIds.stream().filter(id -> !itemOrcamentoIds.contains(id)).toList();
    }

    private void addNewItemOrcamento(ItemOrcamento item){
        List<Custo> custosItem = item.getCustos();
        log.info("ITEM ORCAMENTO: Item orcamento de tipo {}, tem {} custos", item, custosItem.size());
        if (Objects.isNull(item.getId())){
            item = save(item);
        }
        ItemOrcamento finalItem = item;
        custosItem.forEach(custo -> custo.setItemOrcamento(finalItem));
        item.setCustos(saveAllCustosItem(custosItem));
    }

    private List<Custo> saveAllCustosItem(List<Custo> custos){
        verifyAllCustos(custos);
        return custoService.saveAll(custos);
    }

    private void verifyAllCustos(List<Custo> custos){
        removeInactivesCustos(custos);
        custos.forEach(this::addNewCusto);
    }

    private void addNewCusto(Custo custo){
        if (Objects.isNull(custo.getId())){
            custo = custoService.save(custo);
        }
    }

    private void removeInactivesCustos(List<Custo> custos) {
        if (custos.isEmpty()){
            log.error("ITEM ORCAMENTO: nenhum custo relacionado ao item orcamento");
            return;
        }

        Integer itemOrcamentoId = custos.get(0).getItemOrcamento().getId();
        List<Custo> allCustos = custoService.findAllByItemOrcamentoId(itemOrcamentoId);
        List<Integer> custosToBeDeleted = inactivesCustosIds(allCustos, custos);

        log.info("ITEM ORCAMENTO: removendo {} custos inativos", custosToBeDeleted.size());
        custoService.deleteAllByIds(custosToBeDeleted);
    }

    private List<Integer> inactivesCustosIds(List<Custo> allCustos, List<Custo> custos){
        List<Integer> allCustosIds = allCustos.stream().map(Custo::getId).toList();
        List<Integer> custosIds = custos.stream().map(Custo::getId).toList();

        return allCustosIds.stream().filter(custoId -> !custosIds.contains(custoId)).toList();
    }

    public void deleteById(Integer id) {
        if (!repository.existsById(id)){
            throw new ResourceNotFoundException("Item orcamento não encontrado!");
        }
        repository.deleteById(id);
    }
}
