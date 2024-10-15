package com.bridee.api.mapper.response;

import com.bridee.api.dto.response.CasalOrcamentoResponseDto;
import com.bridee.api.dto.response.ItemOrcamentoResponseDto;
import com.bridee.api.dto.response.OrcamentoFornecedorResponseDto;
import com.bridee.api.entity.Casal;
import com.bridee.api.entity.ItemOrcamento;
import com.bridee.api.entity.OrcamentoFornecedor;
import com.bridee.api.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CasalOrcamentoResponseMapper extends BaseMapper<CasalOrcamentoResponseDto, Casal> {

    @Override
    @Mapping(target = "orcamentoGasto", expression = "java(totalOrcamento(entity))")
    @Mapping(target = "orcamentoFornecedores", expression = "java(convertToOrcamentoFornecedorResponseDto(entity.getOrcamentoFornecedores()))")
    @Mapping(target = "itemOrcamentos", expression = "java(convertToItemOrcamentoResponseDto(entity.getItemOrcamentos()))")
    CasalOrcamentoResponseDto toDomain(Casal entity);

    default List<OrcamentoFornecedorResponseDto> convertToOrcamentoFornecedorResponseDto(List<OrcamentoFornecedor> orcamentoFornecedores){
            return orcamentoFornecedores.stream().map(OrcamentoFornecedorResponseDto::new).toList();
    }

    default List<ItemOrcamentoResponseDto> convertToItemOrcamentoResponseDto(List<ItemOrcamento> itemOrcamentos){
        return itemOrcamentos.stream().map(ItemOrcamentoResponseDto::new).toList();
    }

    default BigDecimal totalOrcamento(Casal casais){
        double precoTotalItemOrcamentos = casais.getItemOrcamentos().stream()
                .mapToDouble(item -> item.getCustos().stream()
                .mapToDouble(custo -> Double.parseDouble(custo.getPrecoAtual().toString())).sum()).sum();
        double precoTotalFornecedores = casais.getOrcamentoFornecedores().stream().mapToDouble(orcamentoFornecedor ->
                Double.parseDouble(orcamentoFornecedor.getPreco().toString())).sum();

        return new BigDecimal(precoTotalItemOrcamentos)
                .add(new BigDecimal(precoTotalFornecedores));
    }
}
