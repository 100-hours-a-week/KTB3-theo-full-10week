package com.example.KTB_7WEEK.app.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ArticleImageStorage extends ImageStorage {

    private final Path uploadDir;

    public ArticleImageStorage(@Value("${file.upload-dir.article: uploads/article}")
                                       String uploadDir) throws IOException {
        this.uploadDir = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(this.uploadDir);
    }

    public String saveArticleImage(MultipartFile multipartFile) {
        String newFilename = super.saveImage(multipartFile, uploadDir);
        return newFilename;
    }

    public String updateProfileImage(MultipartFile multipartFile, String oldFileName) {
        String updatedFilename = super.updateImage(multipartFile, uploadDir, oldFileName);
        return updatedFilename;
    }
}
