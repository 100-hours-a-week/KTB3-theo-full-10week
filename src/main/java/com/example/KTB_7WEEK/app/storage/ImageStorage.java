package com.example.KTB_7WEEK.app.storage;

import com.example.KTB_7WEEK.app.exception.common.ImageNotFoundException;
import com.example.KTB_7WEEK.app.exception.common.TooLargeImageException;
import com.example.KTB_7WEEK.user.exception.SaveProfileImageFailException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageStorage implements FileStorage {


    @Value("${spring.servlet.multipart.max-file-size: 40MB}")
    private String maxFileSize;

    public ImageStorage() {

    }

    @Override
    public String saveImage(MultipartFile multipartFile, Path uploadDir) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            return "";
        }
        if (multipartFile.getSize() > getMaxFileSize()) {
            throw new TooLargeImageException();
        }

        String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String ext = "";

        int dotIndex = filename.lastIndexOf(".");
        if (dotIndex != -1) {
            ext = filename.substring(dotIndex);
        }

        String newFilename = UUID.randomUUID() + ext;
        Path target = uploadDir.resolve(newFilename);

        try {
            multipartFile.transferTo(target.toFile());
        } catch (IOException e) {
            throw new SaveProfileImageFailException();
        }
        return newFilename;
    }

    private long getMaxFileSize() {
        long fileSize = Long.parseLong(this.maxFileSize.substring(0, maxFileSize.length() - 2));
        String metric = this.maxFileSize.substring(maxFileSize.length() - 2);

        switch (metric) {
            case "KB":
                fileSize *= 1024;
                break;
            case "MB":
                fileSize *= (1024 * 1024);
                break;
            case "GB":
                fileSize *= (1024 * 1024 * 1024);
                break;
        }
        return fileSize;
    }

    @Override
    public String updateImage(MultipartFile multipartFile, Path uploadDir, String oldFileName) {
//        String oldFileName = extractFileName(path);
        String newFileName = saveImage(multipartFile, uploadDir);
        boolean isDelete = deleteImage(uploadDir, oldFileName);
        return newFileName;
    }

    @Override
    public boolean deleteImage(Path uploadDir, String filename) {
        if(filename == null || filename.isBlank()) return false;
        try {
            Path file = uploadDir.resolve(StringUtils.cleanPath(filename)).normalize();
            return Files.deleteIfExists(file);
        } catch (IOException e) {
            return false;
        }
    }

    private String extractFileName(String path) {
        return Paths.get(path).getFileName().toString();
    }
}
