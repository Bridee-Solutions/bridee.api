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

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrcamentoFornecedorRequestMapper extends BaseMapper<OrcamentoFornecedorRequestDto, OrcamentoFornecedor> {

    @Override
    default OrcamentoFornecedor toEntity(OrcamentoFornecedorRequestDto domain){

        if (domain == null){
            return null;
        }
        return OrcamentoFornecedor.builder()
                .id(domain.getId())
                .preco(domain.getPreco())
                .casal(generateCasal(domain.getCasalId()))
                .fornecedor(generateFornecedor(domain.getFornecedorId()))
                .build();
    };

    default Casal generateCasal(Integer casalId){
        return Casal.builder()
                .id(casalId)
                .build();
    }

    default Fornecedor generateFornecedor(Integer fornecedorId){
        return Fornecedor.builder()
                .id(fornecedorId)
                .build();
    }


}
