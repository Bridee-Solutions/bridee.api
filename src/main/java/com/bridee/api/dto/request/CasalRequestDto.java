package com.bridee.api.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CasalRequestDto extends UsuarioRequestDto{

    private String nomeParceiro;
    private String telefoneParceiro;
    private String endereco;
    private String cep;

}
