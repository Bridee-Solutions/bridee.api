package com.bridee.api.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "aws")
public record AwsConfigInfo(
        SnsConfig sns,
        S3Config s3
) {}

