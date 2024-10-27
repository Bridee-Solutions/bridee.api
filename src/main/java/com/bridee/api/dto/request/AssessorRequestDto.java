package com.bridee.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.br.CNPJ;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "Assessor Request Dto",
description = "DTO para enviar informações dos assessores")
public class AssessorRequestDto extends UsuarioRequestDto{

    @CNPJ
    @NotBlank
    @Schema(description = "CNPJ do assessor", example = "17215584000115")
    private String cnpj;
    @NotBlank
    @Schema(description = "Email da empresa", example = "empresaxpto@example.com")
    private String emailEmpresa;
    @Schema(description = "Valor da assessoria", example = "3000.0")
    private BigDecimal preco;
    @Schema(description = "Se é um assessor premium", example = "true")
    private Boolean premium;

}
