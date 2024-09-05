package com.bridee.api.mapper;

import org.mapstruct.Mapper;

import com.bridee.api.dto.request.FornecedorRequestDto;
import com.bridee.api.entity.Fornecedor;

@Mapper(componentModel = "spring")
public interface FornecedorRequestMapper extends BaseMapper<FornecedorRequestDto, Fornecedor> {
}
