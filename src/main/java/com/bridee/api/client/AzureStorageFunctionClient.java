package com.bridee.api.client;

import com.bridee.api.client.dto.request.FileRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "blobStorageFunction", url = "${services.bridee.function.storage-function.url}")
public interface AzureStorageFunctionClient {

    @PostMapping("/uploadFile")
    ResponseEntity<Void> uploadFile(@RequestBody FileRequest request,
                                    @RequestHeader("x-api-key") String apiKey);

    @GetMapping("/downloadFile")
    ResponseEntity<byte[]> downloadFile(@RequestBody FileRequest request,
                                        @RequestHeader("x-api-key") String apiKey);
}
