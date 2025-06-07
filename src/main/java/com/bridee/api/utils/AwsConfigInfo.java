package com.bridee.api.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "bucket")
public record AwsConfigInfo(
        String name,
        String accessKey,
        String secretKey,
        String sessionToken,
        String region
)
{}
