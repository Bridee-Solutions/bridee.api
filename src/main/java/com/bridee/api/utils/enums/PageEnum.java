package com.bridee.api.utils.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PageEnum {

    PAGE_NUMBER("page"), SIZE("size"), SORT("sort");

    private final String value;

}
