package com.i5.ds.Upload;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class FileTransferService {

    private static final String API_URL = "https://axpt92hqzxmy.objectstorage.ap-chuncheon-1.oci.customer-oci.com/p/stPzlcw3UkTglhF-VQFSXnJFGIj1uT8N-Uy4aikDG5YIWSRU_yLMUfKOTtizCVeQ/n/axpt92hqzxmy/b/bucket_ds/o/image/";

    public ResponseEntity<Map<String, String>> uploadFileToExternalApi(MultipartFile file, String recipeName) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/octet-stream");

        // 랜덤 문자열 생성
        String randomStringForRecipeName = generateRandomString(recipeName.length());
        String randomStringForFile = generateRandomString(8);

        // 파일 이름과 확장자 추출
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            int lastDotIndex = originalFilename.lastIndexOf('.');
            extension = originalFilename.substring(lastDotIndex); // . 포함
        }

        // 새로운 파일 이름 생성
        String fileName = randomStringForRecipeName + "_" + randomStringForFile + extension;

        HttpEntity<byte[]> requestEntity = new HttpEntity<>(file.getBytes(), headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                API_URL + fileName,
                HttpMethod.PUT,
                requestEntity,
                String.class
        );

        System.out.println(responseEntity);
        System.out.println(responseEntity.getBody());
        System.out.println(responseEntity.getHeaders());
        Map<String, String> responseMap = new HashMap<>();
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            responseMap.put("fileName", fileName);
            responseMap.put("responseBody", responseEntity.getBody());
            return ResponseEntity.ok(responseMap);
        } else {
            responseMap.put("fileName", fileName);
            responseMap.put("responseBody", "파일 전송 실패: " + responseEntity.getStatusCode());
            return ResponseEntity.status(500).body(responseMap);
        }
    }

    public String generateRandomString(int length) {
        String characters = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }
}
