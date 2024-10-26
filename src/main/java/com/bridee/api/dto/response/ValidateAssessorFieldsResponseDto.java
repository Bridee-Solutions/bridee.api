package com.bridee.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "DTO de validação de campos",
        description = "Dto para retornar se os campos de um assessor é valido")
public class ValidateAssessorFieldsResponseDto {

    @Schema(description = "Email empresa válido", example = "true")
    private Boolean emailEmpresaExists;
    @Schema(description = "Cnpj válido", example = "true")
    private Boolean cnpjEmpresaExists;

}
