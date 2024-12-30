package com.bridee.api.mapper.request;

import com.bridee.api.dto.request.CustoItemOrcamentoRequestDto;
import com.bridee.api.dto.request.ItemOrcamentoRequestDto;
import com.bridee.api.entity.Casal;
import com.bridee.api.entity.Custo;
import com.bridee.api.entity.ItemOrcamento;
import com.bridee.api.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ItemOrcamentoRequestMapper extends BaseMapper<ItemOrcamentoRequestDto, ItemOrcamento> {

    @Mapping(target = "casal", source = "casamentoId", qualifiedByName = "createCasalEntity")
    default List<ItemOrcamento> toEntity(List<ItemOrcamentoRequestDto> domain, Casal casal){
        if (domain == null){
            return null;
        }

        return domain.stream().map((item) -> {
            return ItemOrcamento.builder()
                    .id(item.getId())
                    .tipo(item.getTipo())
                    .casal(casal)
                    .custos(custos(item.getCustos(), item.getId()))
                    .build();
        }).toList();
    };

    default List<Custo> custos(List<CustoItemOrcamentoRequestDto> custos, Integer itemOrcamentoId){
        if (custos == null){
            return null;
        }

        return custos.stream().map((custo) -> {
            return Custo.builder()
                    .id(custo.getId())
                    .itemOrcamento(ItemOrcamento.builder()
                            .id(itemOrcamentoId)
                            .build())
                    .nome(custo.getNome())
                    .precoAtual(custo.getPrecoAtual())
                    .build();
        }).toList();
    }

    @Named("createCasalEntity")
    default Casal createCasal(Integer casalId){
        return new Casal(casalId);
    }

}
