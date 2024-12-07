package com.bridee.api.pattern.strategy.blobstorage;

import org.springframework.web.multipart.MultipartFile;

public interface BlobStorageStrategy {

    byte[] downloadFile(String filename);
    void uploadFile(MultipartFile multipartFile);
    void uploadFile(MultipartFile multipartFile, String filename);

}
