package com.bridee.api.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TarefaCategoriaEnum {
    FOTOGRAFIA("Fotografia"), CABELO_E_MAQUIAGEM("Cabelo e maquiagem"), VESTIDOS("Vestidos"),
    LOCAIS("Locais"), MUSICA("Musica"), PLANEJADOR("Planejador");

    private final String value;

}
