package com.bridee.api.mapper.request;

import com.bridee.api.dto.request.OrcamentoFornecedorRequestDto;
import com.bridee.api.entity.Casal;
import com.bridee.api.entity.Fornecedor;
import com.bridee.api.entity.OrcamentoFornecedor;
import com.bridee.api.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrcamentoFornecedorRequestMapper extends BaseMapper<OrcamentoFornecedorRequestDto, OrcamentoFornecedor> {

    default List<OrcamentoFornecedor> toEntity(List<OrcamentoFornecedorRequestDto> domain, Casal casal){
        if (domain == null){
            return null;
        }
        return domain.stream().map(orcamento -> {
            return OrcamentoFornecedor.builder()
                    .id(orcamento.getId())
                    .preco(orcamento.getPreco())
                    .casal(casal)
                    .fornecedor(generateFornecedor(orcamento.getFornecedorId()))
                    .build();
        }).toList();
    };

    default OrcamentoFornecedor toEntity(OrcamentoFornecedorRequestDto domain, Casal casal){
        if (domain == null){
            return null;
        }

        return OrcamentoFornecedor.builder()
                    .id(domain.getId())
                    .preco(domain.getPreco())
                    .casal(casal)
                    .fornecedor(generateFornecedor(domain.getFornecedorId()))
                    .build();
    };

    default Fornecedor generateFornecedor(Integer fornecedorId){
        return Fornecedor.builder()
                .id(fornecedorId)
                .build();
    }


}
