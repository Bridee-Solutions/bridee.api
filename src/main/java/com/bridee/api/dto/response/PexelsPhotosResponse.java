package com.bridee.api.dto.response;

import com.bridee.api.client.dto.response.PhotoSource;
import lombok.Data;

@Data
public class PexelsPhotosResponse {

    private Long id;
    private String photographer;
    private String photographerUrl;
    private Long photographerId;
    private PhotoSource source;
    private String altText;
    private boolean isFavorite;

}
