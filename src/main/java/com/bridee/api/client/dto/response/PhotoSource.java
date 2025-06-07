package com.bridee.api.client.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

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

    public List<String> findAllUrls(){
        return List.of(
                original,
                large2x,
                large,
                medium,
                small
        );
    }

}
