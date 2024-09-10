package com.i5.ds.Upload;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.client.RestTemplate;

@Service
public class OraclePARUploadService {

    private final RestTemplate restTemplate = new RestTemplate();

    public void uploadFile(MultipartFile file, String parUrl) {
        try {
            // Get the MIME type of the file
            String mimeType = file.getContentType();
            if (mimeType == null) {
                mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE; // Fallback to generic binary type
            }
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(mimeType));

            // Convert MultipartFile to ByteArrayResource
            ByteArrayResource byteArrayResource = new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            };

            HttpEntity<ByteArrayResource> requestEntity = new HttpEntity<>(byteArrayResource, headers);

            ResponseEntity<String> response = restTemplate.exchange(parUrl, HttpMethod.PUT, requestEntity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("File uploaded successfully.");
            } else {
                System.out.println("Failed to upload file: " + response.getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
