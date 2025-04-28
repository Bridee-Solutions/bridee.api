package com.bridee.api.dto.response;

import lombok.Data;

@Data
public class PhotoSourceResponse {

    private String original;
    private String large2x;
    private String large;
    private String medium;
    private String small;
}
