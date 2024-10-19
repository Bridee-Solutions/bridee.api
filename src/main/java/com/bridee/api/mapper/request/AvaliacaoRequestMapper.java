package com.bridee.api.mapper.request;

import com.bridee.api.dto.request.AvaliacaoRequestDto;
import com.bridee.api.entity.Assessor;
import com.bridee.api.entity.Avaliacao;
import com.bridee.api.entity.Casal;
import com.bridee.api.entity.Fornecedor;
import com.bridee.api.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AvaliacaoRequestMapper extends BaseMapper<AvaliacaoRequestDto, Avaliacao> {

    @Override
    @Mapping(target = "casal", source = "casalId", qualifiedByName = "createCasalEntity")
    @Mapping(target = "fornecedor", source = "fornecedorId",qualifiedByName = "createFornecedorEntity")
    @Mapping(target = "assessor", source = "assessorId",qualifiedByName = "createAssessorEntity")
    Avaliacao toEntity(AvaliacaoRequestDto domain);

    @Named("createCasalEntity")
    default Casal createCasalEntity(Integer casalId){
        if (casalId == null){
            return null;
        }

        return Casal.builder()
                .id(casalId)
                .build();
    }

    @Named("createFornecedorEntity")
    default Fornecedor createFornecedorEntity(Integer fornecedorId){
        if (fornecedorId == null){
            return null;
        }
        return Fornecedor.builder()
                .id(fornecedorId)
                .build();
    }

    @Named("createAssessorEntity")
    default Assessor createAssessorEntity(Integer assessorId){
        if (assessorId == null){
            return null;
        }
        return Assessor.builder()
                .id(assessorId)
                .build();
    }

}
