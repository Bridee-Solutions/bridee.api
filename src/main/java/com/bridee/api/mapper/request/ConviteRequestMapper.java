package com.bridee.api.mapper.request;

import com.bridee.api.dto.request.ConviteRequestDto;
import com.bridee.api.entity.Convite;
import com.bridee.api.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ConviteRequestMapper extends BaseMapper<ConviteRequestDto, Convite> {
}
