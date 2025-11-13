package com.example.KTB_7WEEK.app.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

public interface FileStorage {
    String saveImage(MultipartFile multipartFile, Path uploadDir);

    String updateImage(MultipartFile multipartFile, Path uploadDir, String oldFileName);

    boolean deleteImage(Path uploadDir, String fileName);
}
