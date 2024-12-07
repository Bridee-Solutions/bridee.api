package com.bridee.api.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TarefaStatusEnum {
    CONCLUIDO("Concluido"), EM_ANDAMENTO("Em andamento");

    private final String value;

}
