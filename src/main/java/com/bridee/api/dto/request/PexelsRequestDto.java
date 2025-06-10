package com.bridee.api.dto.request;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Data
public class PexelsRequestDto {

    private String query;
    private String perPage;
    private String page;

    public PexelsRequestDto(String query, String perPage, String page) {
        this.query = query;
        this.perPage = perPage;
        this.page = page;
    }

    public Map<String, String> getFilter(){
        Map<String, String> filter = new HashMap<>();
        if(StringUtils.isNotBlank(query)){
            filter.put("query", query);
        }
        if(StringUtils.isNotBlank(perPage)){
            filter.put("per_page", perPage);
        }
        if(StringUtils.isNotBlank(page)){
            filter.put("page", page);
        }
        return filter;
    }

}
