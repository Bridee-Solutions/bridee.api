package com.bridee.api.dto.response.externo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CasalExternoResponseDto extends UsuarioExternoResponseDto{

    private String nomeParceiro;
    private String telefoneParceiro;
    private String endereco;
    private String cep;

}
