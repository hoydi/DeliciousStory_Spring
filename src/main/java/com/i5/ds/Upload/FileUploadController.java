package com.i5.ds.Upload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class FileUploadController {

    @Autowired
    private FileTransferService fileTransferService;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("recipeName") String recipeName) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("fileUrl", "", "responseBody", "파일을 선택해 주세요."));
        }

        try {
            // 파일과 레시피 이름을 전달하여 응답 생성
            return fileTransferService.uploadFileToExternalApi(file, recipeName);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("fileUrl", "", "responseBody", "파일 전송 실패: " + e.getMessage()));
        }
    }
}
