package com.bridee.api.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("azure.data-storage.container")
public class AzureBlobStorageProperties {

    private String url;
    private String sasToken;

    public String generateImageUrlByImageName(String imageName){
        return "%s/%s?%s".formatted(url, imageName, sasToken);
    }

}
