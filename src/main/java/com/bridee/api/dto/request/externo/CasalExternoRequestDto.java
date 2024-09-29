package com.bridee.api.dto.request.externo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CasalExternoRequestDto extends UsuarioExternoRequestDto {

    private String nomeParceiro;
    private String telefoneParceiro;
    private String endereco;
    private String cep;

}
