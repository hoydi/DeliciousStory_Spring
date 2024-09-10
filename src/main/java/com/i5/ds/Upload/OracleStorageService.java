package com.i5.ds.Upload;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class OracleStorageService {

    private final RestTemplate restTemplate = new RestTemplate();

    public String uploadFile(MultipartFile file) throws Exception {
        String bucketName = "bucket_ds";
        String namespace = "axpt92hqzxmy";
        String objectName = file.getOriginalFilename();
        String url = String.format("https://objectstorage.%s.oraclecloud.com/n/%s/b/%s/o/%s",
                "ap-chuncheon-1", namespace, bucketName, objectName);

        // 파일을 로컬 임시 파일에 저장
        Path tempFile = Files.createTempFile("upload-", file.getOriginalFilename());
        Files.copy(file.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);

        // HTTP 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("Authorization", "Bearer KfcZHOFN549sdz4tSjGgnQbXCTGn8GuJRsd1shIU2YySIwYjxOH6hybSg5ezzlrC");  // API 토큰 또는 서명 추가

        HttpEntity<byte[]> requestEntity = new HttpEntity<>(Files.readAllBytes(tempFile), headers);

        // REST API 호출
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);

        return response.getBody();
    }
}
