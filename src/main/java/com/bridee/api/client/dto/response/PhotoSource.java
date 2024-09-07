package com.bridee.api.client.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PhotoSource {

    @JsonProperty("original")
    private String original;
    @JsonProperty("large2x")
    private String large2x;
    @JsonProperty("large")
    private String large;
    @JsonProperty("medium")
    private String medium;
    @JsonProperty("small")
    private String small;
}
