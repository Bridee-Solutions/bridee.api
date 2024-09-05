package com.bridee.api.mapper;

import com.bridee.api.dto.response.FornecedorResponseDto;
import com.bridee.api.entity.Fornecedor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FornecedorResponseMapper extends BaseMapper<FornecedorResponseDto, Fornecedor> {
}
