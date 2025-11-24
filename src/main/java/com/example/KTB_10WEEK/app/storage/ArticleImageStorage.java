package com.example.KTB_10WEEK.app.storage;

import com.example.KTB_10WEEK.app.exception.common.SaveImageFailException;
import com.example.KTB_10WEEK.post.exception.SaveArticleImageFailException;
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
        try {
            String newFilename = super.saveImage(multipartFile, uploadDir);
            return newFilename;
        } catch (SaveImageFailException e) {
            throw new SaveArticleImageFailException();
        }

    }

    public String updateArticleImage(MultipartFile multipartFile, String oldFileName) {
        String updatedFilename = super.updateImage(multipartFile, uploadDir, oldFileName);
        return updatedFilename;
    }

    public boolean deleteArticleImage(String fileName) {
        return super.deleteImage(uploadDir,fileName);
    }
}
