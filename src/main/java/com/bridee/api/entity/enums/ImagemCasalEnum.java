package com.bridee.api.entity.enums;

import lombok.Getter;

@Getter
public enum ImagemCasalEnum {
    PERFIL("Perfil"), FAVORITO("Favorito");

    private final String value;

    ImagemCasalEnum(String value) {
        this.value = value;
    }
}
