package com.bridee.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.br.CNPJ;

@Data
public class ValidateAssessorFieldsRequestDto {

    @Email
    @NotBlank
    private String emailEmpresa;
    @CNPJ
    @NotBlank
    private String cnpj;

}
