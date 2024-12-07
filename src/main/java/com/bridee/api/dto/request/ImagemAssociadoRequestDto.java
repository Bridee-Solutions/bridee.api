package com.bridee.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(name = "DTO de imagem do assessor",
        description = "DTO para atualizar e receber e atualizar imagens do assessor")
public class ImagemAssociadoRequestDto {
    private Integer fkImagem;
    private String tipo;
}
