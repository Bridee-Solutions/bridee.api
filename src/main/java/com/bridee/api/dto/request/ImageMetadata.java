package com.bridee.api.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ImageMetadata {

    private Integer id;
    private String nome;
    private LocalDateTime dataCriacao;
    private String tipo;
    private String extensao;

}
