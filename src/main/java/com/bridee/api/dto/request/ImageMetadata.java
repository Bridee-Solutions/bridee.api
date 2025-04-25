package com.bridee.api.dto.request;

import com.bridee.api.entity.enums.ImagemCasalEnum;
import lombok.Data;

@Data
public class ImageMetadata {

    private Integer id;
    private String nome;
    private String url;
    private ImagemCasalEnum tipo;
    private String extensao;

}
