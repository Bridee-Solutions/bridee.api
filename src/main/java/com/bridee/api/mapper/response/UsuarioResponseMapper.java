package com.bridee.api.mapper.response;

import com.bridee.api.dto.response.UsuarioResponseDto;
import com.bridee.api.entity.Usuario;
import com.bridee.api.mapper.BaseMapper;
import com.bridee.api.repository.projection.usuario.UserDetailsProjection;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UsuarioResponseMapper extends BaseMapper<UsuarioResponseDto, Usuario> {

    Usuario fromProjection(UserDetailsProjection userDetailsProjection);

}
