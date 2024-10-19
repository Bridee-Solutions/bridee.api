package com.bridee.api.mapper.request;

import com.bridee.api.dto.request.FornecedorOrcamentoRequestDto;
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
public interface FornecedorOrcamentoRequestMapper extends BaseMapper<FornecedorOrcamentoRequestDto, OrcamentoFornecedor> {

    @Override
    @Mapping(target = "fornecedor", source = "fornecedorId", qualifiedByName = "createFornecedorEntity")
    @Mapping(target = "casal", source = "casalId", qualifiedByName = "createCasalEntity")
    OrcamentoFornecedor toEntity(FornecedorOrcamentoRequestDto domain);

    @Named("createFornecedorEntity")
    default Fornecedor createFornecedor(Integer fornecedorId){
        return Fornecedor.builder()
                .id(fornecedorId)
                .build();
    }

    @Named("createCasalEntity")
    default Casal createCasal(Integer casalId){
        return Casal.builder()
                .id(casalId)
                .build();
    }

}
