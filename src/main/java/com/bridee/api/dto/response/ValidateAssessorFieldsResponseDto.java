package com.bridee.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidateAssessorFieldsResponseDto {

    private Boolean emailEmpresaExists;
    private Boolean cnpjEmpresaExists;

}
