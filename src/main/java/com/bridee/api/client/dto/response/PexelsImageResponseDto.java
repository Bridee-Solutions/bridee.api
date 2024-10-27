package com.bridee.api.client.dto.response;

import com.bridee.api.utils.ListaObj;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PexelsImageResponseDto {

    @JsonProperty("total_results")
    private Long totalResults;

    @JsonProperty("page")
    private Integer page;

    @JsonProperty("per_page")
    private Integer perPage;

    @JsonProperty("photos")
    private ListaObj<PexelsPhotos> photos;

    @JsonProperty("next_page")
    private String nextPageUrl;

}
