package com.bridee.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PexelsImageResponse {

    private Long totalResults;
    private Integer page;
    private Integer perPage;
    private List<PexelsPhotosResponse> photos;
    private String nextPageUrl;

}
