package com.bridee.api.dto.request.externo;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CasalExternoRequestDto extends UsuarioExternoRequestDto {

    @Size(min = 3)
    private String nomeParceiro;
    @Size(min = 11, max = 11)
    private String telefoneParceiro;
    private String endereco;
    @Size(min = 8, max = 8)
    private String cep;

}
