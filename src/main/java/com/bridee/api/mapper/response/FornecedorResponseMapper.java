package com.bridee.api.mapper.response;

import com.bridee.api.mapper.BaseMapper;
import org.mapstruct.Mapper;

import com.bridee.api.dto.response.FornecedorResponseDto;
import com.bridee.api.entity.Fornecedor;

@Mapper(componentModel = "spring")
public interface FornecedorResponseMapper extends BaseMapper<FornecedorResponseDto, Fornecedor> {
}