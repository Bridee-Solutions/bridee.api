package com.bridee.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConviteResumoResponseDto {

    private Integer totalConvites;
    private Integer totalConfirmado;
    private Integer totalConvidados;
    private Integer totalAdultos;
    private Integer totalCriancas;
    private List<CategoriaConvidadoResumoDto> resumoCategorias;

}
