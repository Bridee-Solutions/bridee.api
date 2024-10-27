package com.bridee.api.dto.request.externo;

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
@Schema(name = "DTO assessor externo",
        description = "DTO para enviar informações de um assessor externo")
public class AssessorExternoRequestDto extends UsuarioExternoRequestDto {

    @CNPJ
    @NotBlank
    @Schema(description = "Cnpj do assessor", example = "17215584000115")
    private String cnpj;
    @Positive
    @NotNull
    @Schema(description = "Valor do serviço", example = "3000.0")
    private BigDecimal preco;
    @Schema(description = "Informa se o usuário é premium", example = "true")
    private Boolean premium;

}
