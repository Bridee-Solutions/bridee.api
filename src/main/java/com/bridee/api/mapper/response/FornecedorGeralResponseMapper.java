package com.bridee.api.mapper.response;

import com.bridee.api.projection.fornecedor.FornecedorGeralResponseDto;
import com.bridee.api.projection.fornecedor.FornecedorGeralResponseProjection;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FornecedorGeralResponseMapper {

    FornecedorGeralResponseDto toGeralDto(FornecedorGeralResponseProjection projection);
}
