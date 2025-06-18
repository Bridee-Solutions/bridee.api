package com.bridee.api.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "aws.s3")
public record S3Config(
        String name,
        String accessKey,
        String secretKey,
        String sessionToken,
        String region
){}
