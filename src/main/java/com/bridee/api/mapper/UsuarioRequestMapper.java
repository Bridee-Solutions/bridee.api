package com.bridee.api.mapper;

import org.mapstruct.Mapper;

import com.bridee.api.dto.request.UsuarioRequestDto;
import com.bridee.api.entity.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioRequestMapper extends BaseMapper<UsuarioRequestDto, Usuario> {

}
