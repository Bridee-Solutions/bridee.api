package com.bridee.api.configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.bridee.api.utils.AwsConfigInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfig {

    private final AwsConfigInfo config;

    public AwsConfig(AwsConfigInfo config) {
        this.config = config;
    }

    @Bean
    public AmazonS3 amazonS3() {
        AWSStaticCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials());
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(credentialsProvider)
                .withRegion(config.region())
                .build();
    }

    private AWSCredentials credentials(){
        return new BasicSessionCredentials(
                config.accessKey(),
                config.secretKey(),
                config.sessionToken()
        );
    }
}
