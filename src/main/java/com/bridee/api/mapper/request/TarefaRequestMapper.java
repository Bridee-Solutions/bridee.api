package com.bridee.api.mapper.request;

import com.bridee.api.dto.request.TarefaRequestDto;
import com.bridee.api.entity.Tarefa;
import com.bridee.api.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TarefaRequestMapper extends BaseMapper<TarefaRequestDto, Tarefa> {
}
