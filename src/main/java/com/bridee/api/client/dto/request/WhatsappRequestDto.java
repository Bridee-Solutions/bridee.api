package com.bridee.api.client.dto.request;

import com.bridee.api.client.enums.WhatsappClientType;
import com.bridee.api.client.enums.WhatsappMessageType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    @JsonProperty("client_type")
    private WhatsappClientType whatsappClientType;

    private String pinCode;

}
