package com.bridee.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(name = "DTO de configurações do assessor",
        description = "DTO para atualizar e receber e atualizar dados do assessor")
public class InformacaoAssociadoRequestDto {

    private Integer id;

    private String nomeComercial;

    @Schema(description = "Visão geral do negócio do assessor")
    private String visaoGeral;

    @Schema(description = "Atributo que informa caso o assessor aceita casamentos não religiosos")
    private Boolean naoReligioso;

    @Schema(description = "Lista de serviços do negócio do assessor")
    private String servicosOferecidos;

    @Schema(description = "Descrição da forma de trabalho do assessor")
    private String formaDeTrabalho;

    @Schema(description = "Tamanho de casamentos no qual o assessor trabalha")
    private String tamanhoCasamento;

    @Schema(description = "Email comercial do assessor", example = "agdapaulacasamentos@gmail.com")
    private String email;

    @Schema(description = "Url de acesso para a pagina do assessor", example = "/AgdaPaula")
    private String urlSite;

    @Schema(description = "Cidade onde o assessor trabalha")
    private String cidade;

    @Schema(description = "Url de acesso para a pagina do assessor")
    private String bairro;
}
