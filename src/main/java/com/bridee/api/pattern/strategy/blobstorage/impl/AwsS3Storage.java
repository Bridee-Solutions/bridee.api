package com.bridee.api.pattern.strategy.blobstorage.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.bridee.api.exception.BucketDownloadException;
import com.bridee.api.exception.BucketUploadException;
import com.bridee.api.pattern.strategy.blobstorage.BlobStorageStrategy;
import com.bridee.api.utils.AwsConfigInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URL;

@Component
public class AwsS3Storage implements BlobStorageStrategy {

    private final AwsConfigInfo config;
    private final AmazonS3 s3Client;
    private final Logger logger = LoggerFactory.getLogger(AwsS3Storage.class);

    public AwsS3Storage(AmazonS3 s3Client, AwsConfigInfo config) {
        this.s3Client = s3Client;
        this.config = config;
    }

    public void uploadFile(MultipartFile multipartFile, String imageName){
        try{
            InputStream inputStream = multipartFile.getInputStream();
            ObjectMetadata metadata = new ObjectMetadata();
            s3Client.putObject(config.name(), imageName, inputStream, metadata);
        }catch (Exception e){
            logger.error("Houve um erro ao realizar o upload do arquivo {}, com o seguinte erro {}",
                    imageName, e.getMessage());
            throw new BucketUploadException(e.getMessage());
        }
    }

    public String downloadFile(String objectName){
        try{
            URL s3Object = s3Client.getUrl(config.name(), objectName);
            return s3Object.toString();
        }catch (Exception e){
            logger.error("Houve um erro ao tentar realizar o download do arquivo {}, com o seguinte erro {}", objectName, e.getMessage());
            throw new BucketDownloadException(e.getMessage());
        }
    }
}
