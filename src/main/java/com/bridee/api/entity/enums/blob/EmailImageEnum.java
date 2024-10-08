package com.bridee.api.entity.enums.blob;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmailImageEnum {

    CASAL_IMAGE("casal-cadastro.jpg"), LOGO("logo.svg");

    private final String value;
}
