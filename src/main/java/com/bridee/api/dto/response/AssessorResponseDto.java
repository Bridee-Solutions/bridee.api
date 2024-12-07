package com.bridee.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "DTO de assessor",
        description = "DTO para devolver informações dos assessores")
public class AssessorResponseDto extends UsuarioResponseDto{

    @Schema(description = "Cnpj do assessor", example = "17215584000115")
    private String cnpj;
    private String emailEmpresa;
    private String telefoneEmpresa;
    @Schema(example = "true")
    private Boolean premium;

}
