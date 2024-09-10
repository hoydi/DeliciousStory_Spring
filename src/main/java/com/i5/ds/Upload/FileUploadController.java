package com.i5.ds.Upload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController {

    @Autowired
    private OraclePARUploadService oraclePARUploadService;

    private final String parUrl = "https://objectstorage.ap-chuncheon-1.oraclecloud.com/n/axpt92hqzxmy/b/bucket_img/o/";

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // Generate object name based on file original name
            String objectName = file.getOriginalFilename();

            // Complete URL with object name
            String uploadUrl = parUrl + objectName;

            // Upload file to Oracle Cloud
            oraclePARUploadService.uploadFile(file, uploadUrl);

            return "File uploaded successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to upload file: " + e.getMessage();
        }
    }
}
