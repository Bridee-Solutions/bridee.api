package com.bridee.api.mapper.response;

import com.bridee.api.dto.response.TarefaResponseDto;
import com.bridee.api.entity.Tarefa;
import com.bridee.api.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TarefaResponseMapper extends BaseMapper<TarefaResponseDto, Tarefa> {
}
