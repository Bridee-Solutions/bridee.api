package com.bridee.api.dto.request;

import lombok.Data;

@Data
public class ImageMetadata {

    private Integer id;
    private String nome;
    private String url;
    private String tipo;
    private String extensao;

}
