package com.bridee.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WhatsappResponseDto {

    @JsonProperty("result")
    private String result;
    @JsonProperty("message_id")
    private Long messageId;
    @JsonProperty("contact_phone_number")
    private String contactPhoneNumber;
    @JsonProperty("phone_state")
    private String phoneState;
}
