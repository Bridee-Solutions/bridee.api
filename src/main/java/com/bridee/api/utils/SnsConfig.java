package com.bridee.api.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "aws.sns")
public record SnsConfig(
        String topicArn
){}
