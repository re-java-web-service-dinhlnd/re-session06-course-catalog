package com.re.coursecatalog.service.impl;

import com.re.coursecatalog.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public String saveFile(MultipartFile file) throws IOException {
        String contentType = file.getContentType();

        if (contentType == null || !(contentType.equals("image/jpeg") || contentType.equals("image/png") || contentType.equals("image/jpg"))) {
            throw new IllegalArgumentException("Chỉ chấp nhận file ảnh định dạng jpg, jpeg, png");
        }

        File directory = new File(uploadDir);
        if (!directory.exists()){
            directory.mkdirs();
        }

        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename != null ? originalFilename.substring(originalFilename.lastIndexOf(".")) : ".jpg";
        String uniqueFileName = UUID.randomUUID() + extension;

        Path filePath = Paths.get(uploadDir, uniqueFileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return uniqueFileName;
    }

    @Override
    public void deleteFile(String fileName) {
        if(fileName != null && !fileName.isEmpty()){
            try {
                Path filePath = Paths.get(uploadDir, fileName);
                Files.deleteIfExists(filePath);
            }
            catch (IOException e){
                System.err.println("Không thể xóa file: " + fileName);
            }
        }
    }
}
