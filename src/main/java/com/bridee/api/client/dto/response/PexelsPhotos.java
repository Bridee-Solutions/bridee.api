package com.bridee.api.client.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@Data
@EqualsAndHashCode
public class PexelsPhotos implements Comparable<PexelsPhotos> {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("photographer")
    private String photographer;
    @JsonProperty("photographer_url")
    private String photographerUrl;
    @JsonProperty("photographer_id")
    private Long photographerId;
    @JsonProperty("src")
    private PhotoSource source;
    @JsonProperty("alt")
    private String altText;

    @Override
    public int compareTo(@NonNull PexelsPhotos o) {
        return this.getPhotographer().compareTo(o.getPhotographer());
    }
}
