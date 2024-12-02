package com.bridee.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(name = "DTO de configurações do assessor",
        description = "DTO para atualizar e receber e atualizar dados do assessor")
public class InformacaoAssociadoRequestDto {
    @NotBlank
    @Schema(description = "Visão geral do negócio do assessor")
    private String visaoGeral;
    
    @NotBlank
    @Schema(description = "Lista de serviços do negócio do assessor")
    private String servicosOferecidos;
    
    @NotBlank
    @Schema(description = "Descrição da forma de trabalho do assessor")
    private String formaDeTrabalho;
    
    @NotBlank
    @Schema(description = "Tamanho de casamentos no qual o assessor trabalha")
    private String tamanhoCasamento;

    @NotBlank
    @Schema(description = "Email comercial do assessor", example = "agdapaulacasamentos@gmail.com")
    private String email;

    @NotBlank
    @Schema(description = "Url de acesso para a pagina do assessor", example = "/AgdaPaula")
    private String urlSite;

    @NotBlank
    @Schema(description = "Cidade onde o assessor trabalha")
    private String cidade;

    @NotBlank
    @Schema(description = "Url de acesso para a pagina do assessor")
    private String bairro;
}
