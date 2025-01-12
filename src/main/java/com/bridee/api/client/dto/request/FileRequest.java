package com.bridee.api.client.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileRequest {

    private String fileName;
    private String file;

    public FileRequest(String fileName) {
        this.fileName = fileName;
    }
}
