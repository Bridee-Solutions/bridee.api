package com.bridee.api.mapper.response;

import com.bridee.api.dto.response.UsuarioResponseDto;
import com.bridee.api.entity.Usuario;
import com.bridee.api.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UsuarioResponseMapper extends BaseMapper<UsuarioResponseDto, Usuario> {
}
