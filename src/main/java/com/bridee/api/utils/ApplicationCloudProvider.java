package com.bridee.api.utils;

import com.bridee.api.entity.enums.CloudProvider;
import com.bridee.api.pattern.strategy.blobstorage.BlobStorageStrategy;
import com.bridee.api.pattern.strategy.blobstorage.impl.AwsS3Storage;
import com.bridee.api.pattern.strategy.blobstorage.impl.AzureBlobStorageImpl;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ApplicationCloudProvider {

    @Value("${cloud.provider}")
    private String cloudProvider;
    private final Map<CloudProvider, BlobStorageStrategy> blobImplementation = new HashMap<>();
    private final AwsS3Storage awsS3Storage;
    private final AzureBlobStorageImpl azureBlobStorage;

    @PostConstruct
    public void init(){
        blobImplementation.put(CloudProvider.AWS, awsS3Storage);
        blobImplementation.put(CloudProvider.AZURE, azureBlobStorage);
    }

    public BlobStorageStrategy getBlobImplementation(){
        CloudProvider applicationCloudProvider = CloudProvider.getCloudProvider(cloudProvider);
        return this.blobImplementation.get(applicationCloudProvider);
    }

}
