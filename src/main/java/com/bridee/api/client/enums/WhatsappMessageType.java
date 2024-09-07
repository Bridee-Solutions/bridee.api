package com.bridee.api.client.enums;

import lombok.Getter;

@Getter
public enum WhatsappMessageType {
    TEXT("text"), IMAGE("image");

    private final String value;

    WhatsappMessageType(String value) {
        this.value = value;
    }
}
