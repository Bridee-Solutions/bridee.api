package com.bridee.api.pattern.strategy.blobstorage;

import org.springframework.web.multipart.MultipartFile;

public interface BlobStorageStrategy {

    String downloadFile(String filename);
    void uploadFile(MultipartFile multipartFile, String filename);

}
