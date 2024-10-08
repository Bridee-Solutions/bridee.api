package com.bridee.api.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CasalResponseDto extends UsuarioResponseDto{

    private String nomeParceiro;
    private String telefoneParceiro;
    private String endereco;
    private String cep;
}
