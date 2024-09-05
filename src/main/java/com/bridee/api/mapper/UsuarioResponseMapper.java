package com.bridee.api.mapper;

import org.mapstruct.Mapper;
import com.bridee.api.dto.response.UsuarioResponseDto;
import com.bridee.api.entity.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioResponseMapper extends BaseMapper<UsuarioResponseDto, Usuario> {

}
