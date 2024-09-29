package com.bridee.api.mapper.request;

import com.bridee.api.dto.request.UsuarioRequestDto;
import com.bridee.api.entity.Usuario;
import com.bridee.api.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UsuarioRequestMapper extends BaseMapper<UsuarioRequestDto, Usuario> {

}
