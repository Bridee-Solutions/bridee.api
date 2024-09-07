package com.bridee.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WhatsappRequestDto {

    @JsonProperty("apikey")
    private String apiKey;
    @JsonProperty("phone_number")
    private String phoneNumber;
    @JsonProperty("contact_phone_number")
    private String contactPhoneNumber;
    @JsonProperty("message_custom_id")
    private String messageCustomId;
    @JsonProperty("message_type")
    private String messageType;
    @JsonProperty("check_status")
    private String checkStatus;
    @JsonProperty("message_body_mimetype")
    private String messageBodyMimetype;
    @JsonProperty("message_body_filename")
    private String messageBodyFilename;
    @JsonProperty("message_caption")
    private String messageCaption;
    @JsonProperty("message_body")
    private String messageBody;

}
