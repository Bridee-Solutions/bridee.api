package com.bridee.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.br.CNPJ;

@Data
@Schema(name = "DTO para validar os campos de um assessor",
        description = "DTO para enviar os dados do assessor para serem validados durante o cadastro")
public class ValidateAssessorFieldsRequestDto {

    @Email
    @NotBlank
    @Schema(description = "E-mail da empresa", example = "empresaxpto@example.com")
    private String emailEmpresa;
    @CNPJ
    @NotBlank
    @Schema(description = "CNPJ da empresa", example = "17215584000115")
    private String cnpj;

}
