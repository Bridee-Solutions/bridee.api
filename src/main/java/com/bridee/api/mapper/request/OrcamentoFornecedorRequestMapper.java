package com.bridee.api.mapper.request;

import com.bridee.api.dto.request.OrcamentoFornecedorRequestDto;
import com.bridee.api.entity.Casal;
import com.bridee.api.entity.Fornecedor;
import com.bridee.api.entity.OrcamentoFornecedor;
import com.bridee.api.mapper.BaseMapper;
import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.List;
import java.util.Objects;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrcamentoFornecedorRequestMapper extends BaseMapper<OrcamentoFornecedorRequestDto, OrcamentoFornecedor> {

    default List<OrcamentoFornecedor> toEntity(List<OrcamentoFornecedorRequestDto> domain, Integer casalId){
        if (domain == null){
            return null;
        }

        Casal casal = new Casal(casalId);
        return domain.stream().map(orcamento -> {
            return OrcamentoFornecedor.builder()
                    .id(orcamento.getId())
                    .preco(orcamento.getPreco())
                    .casal(casal)
                    .fornecedor(generateFornecedor(orcamento.getFornecedorId()))
                    .build();
        }).toList();
    };

    default OrcamentoFornecedor toEntity(OrcamentoFornecedorRequestDto domain, Integer casalId){
        if (domain == null){
            return null;
        }

        Casal casal = new Casal(casalId);
        return OrcamentoFornecedor.builder()
                    .id(domain.getId())
                    .preco(domain.getPreco())
                    .casal(casal)
                    .fornecedor(generateFornecedor(domain.getFornecedorId()))
                    .build();
    };

    default Fornecedor generateFornecedor(Integer fornecedorId){
        if(Objects.isNull(fornecedorId)){
            return null;
        }
        return Fornecedor.builder()
                .id(fornecedorId)
                .build();
    }

}
