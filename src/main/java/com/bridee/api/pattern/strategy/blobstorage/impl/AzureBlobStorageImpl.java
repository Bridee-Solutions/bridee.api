package com.bridee.api.pattern.strategy.blobstorage.impl;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobProperties;
import com.bridee.api.client.AzureStorageFunctionClient;
import com.bridee.api.client.dto.request.FileRequest;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.exception.UnprocessableEntityException;
import com.bridee.api.pattern.strategy.blobstorage.BlobStorageStrategy;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AzureBlobStorageImpl implements BlobStorageStrategy {

    @Value("${services.bridee.function.storage-function.api-key}")
    private String storageFunctionApiKey;

    private final AzureStorageFunctionClient azureFunctionClient;

    @Override
    public byte[] downloadFile(String filename) {
        FileRequest fileRequest = new FileRequest(filename);
        ResponseEntity<byte[]> downloadResponse = azureFunctionClient.downloadFile(fileRequest, storageFunctionApiKey);
        return downloadResponse.getBody();
    }

    @Override
    public void uploadFile(MultipartFile multipartFile, String filename) {
        FileRequest fileRequest = new FileRequest(filename, toBase64(multipartFile));
        azureFunctionClient.uploadFile(fileRequest, storageFunctionApiKey);
    }

    private String toBase64(MultipartFile multipartFile){
        try{
            return Base64.getEncoder().encodeToString(multipartFile.getInputStream().readAllBytes());
        }catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
