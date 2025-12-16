package com.todayseafood.api.app.storage;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface FileStorage {
    String saveImage(MultipartFile multipartFile, Path uploadDir);

    String updateImage(MultipartFile multipartFile, Path uploadDir, String oldFileName);

    boolean deleteImage(Path uploadDir, String fileName);
}
