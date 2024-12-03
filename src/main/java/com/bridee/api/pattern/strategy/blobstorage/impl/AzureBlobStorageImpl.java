package com.bridee.api.pattern.strategy.blobstorage.impl;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobProperties;
import com.bridee.api.exception.ResourceNotFoundException;
import com.bridee.api.exception.UnprocessableEntityException;
import com.bridee.api.pattern.strategy.blobstorage.BlobStorageStrategy;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class AzureBlobStorageImpl implements BlobStorageStrategy {

    @Value("${azure.blob-storage.connection-string}")
    private String azureConnectionString;

    @Value("${spring.cloud.azure.storage.blob.container-name}")
    private String azureContainerName;

    private BlobServiceClient blobServiceClient;

//    @PostConstruct
//    public void init(){
//        this.blobServiceClient = new BlobServiceClientBuilder().connectionString(azureConnectionString).buildClient();
//    }

    @Override
    public byte[] downloadFile(String filename) {
        byte[] binaries = null;
        BlobClient blobClient = blobServiceClient.getBlobContainerClient(azureContainerName).getBlobClient(filename);
        try{
            binaries = blobClient.downloadContent().toBytes();
        }catch (Exception e){
            e.printStackTrace();
        }
        return binaries;
    }

    @Override
    public void uploadFile(MultipartFile multipartFile) {
        String blobFilename = multipartFile.getOriginalFilename();
        BlobClient blobClient = blobServiceClient.getBlobContainerClient(azureContainerName).getBlobClient(blobFilename);

        try {
            blobClient.upload(multipartFile.getInputStream(), multipartFile.getSize(), true);
        } catch (IOException e) {
            throw new UnprocessableEntityException("Não foi possível realizar o upload da imagem.");
        }
    }

    @Override
    public void uploadFile(MultipartFile multipartFile, String filename) {

        BlobClient blobClient = blobServiceClient.getBlobContainerClient(azureContainerName).getBlobClient(filename);
        try {
            blobClient.upload(multipartFile.getInputStream(), multipartFile.getSize(), true);
        } catch (IOException e) {
            throw new UnprocessableEntityException("Não foi possível realizar o upload da imagem.");
        }

    }

}
