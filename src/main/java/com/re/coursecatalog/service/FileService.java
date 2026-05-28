package com.re.coursecatalog.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    String saveFile(MultipartFile file) throws IOException;

    void deleteFile(String fileName);
}
