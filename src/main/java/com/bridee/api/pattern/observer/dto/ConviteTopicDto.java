package com.bridee.api.pattern.observer.dto;

import com.bridee.api.dto.response.CasalResponseDto;
import com.bridee.api.dto.response.ConvidadoResponseDto;
import com.bridee.api.dto.response.ConviteResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConviteTopicDto {

    private CasalResponseDto casal;
    private ConviteResponseDto convite;
    private ConvidadoResponseDto convidado;

}
