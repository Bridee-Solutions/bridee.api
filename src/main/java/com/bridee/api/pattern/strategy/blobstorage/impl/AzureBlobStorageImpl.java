package com.bridee.api.pattern.strategy.blobstorage.impl;

import com.bridee.api.client.AzureStorageFunctionClient;
import com.bridee.api.client.dto.request.FileRequest;
import com.bridee.api.pattern.strategy.blobstorage.BlobStorageStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class AzureBlobStorageImpl implements BlobStorageStrategy {

    @Value("${services.bridee.function.storage-function.api-key}")
    private String storageFunctionApiKey;

    private final AzureStorageFunctionClient azureFunctionClient;

    @Override
    public String downloadFile(String filename) {
        FileRequest fileRequest = new FileRequest(filename);
        ResponseEntity<String> downloadResponse = azureFunctionClient.downloadFile(fileRequest, storageFunctionApiKey);
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
