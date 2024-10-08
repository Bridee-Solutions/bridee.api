package com.bridee.api.mapper.request;

import com.bridee.api.mapper.BaseMapper;
import org.mapstruct.Mapper;

import com.bridee.api.dto.request.FornecedorRequestDto;
import com.bridee.api.entity.Fornecedor;

@Mapper(componentModel = "spring")
public interface FornecedorRequestMapper extends BaseMapper<FornecedorRequestDto, Fornecedor> {
}
