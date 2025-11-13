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
import java.util.UUID;

@Service
@Transactional
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

        try {
            System.out.println(multipartFile.getSize());
            System.out.println(multipartFile.getBytes().length);
        } catch (IOException e) {

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
    public String updateImage(MultipartFile multipartFile, Path uploadDir) {
        deleteImage(uploadDir);
        return saveImage(multipartFile, uploadDir);
    }

    @Override
    public boolean deleteImage(Path uploadDir) {
        try {
            return Files.deleteIfExists(uploadDir);
        } catch (IOException e) {
            return false;
        }
    }
}
