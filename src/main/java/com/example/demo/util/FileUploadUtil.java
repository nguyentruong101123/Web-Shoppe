package com.example.demo.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUploadUtil {

    public static String saveFile(String uploadDir, MultipartFile multipartFile) throws IOException {
        // Lấy tên tệp từ MultipartFile
        String fileName = multipartFile.getOriginalFilename();
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try {
            Path filePath = uploadPath.resolve(fileName);
            multipartFile.transferTo(filePath.toFile());
            return "/assets/images/avata/" + fileName; // Trả về đường dẫn ảnh
        } catch (IOException e) {
            throw new IOException("Could not save file: " + fileName, e);
        }
    }
}
