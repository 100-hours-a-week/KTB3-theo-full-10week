package com.example.KTB_7WEEK.app.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class LocalFileStorage implements FileStorage{

    private final Path uploadDir;

    public LocalFileStorage(@Value("${file.upload-dir:uploads/profile}")
                                    String uploadDir) throws IOException {
        this.uploadDir = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(this.uploadDir);
    }

    @Override
    public String saveProfileImage(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            return null; // throw new MultipartFileNotFoundException();
        }
        System.out.println(multipartFile.getSize());
        String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String ext = "";

        int dotIndex = filename.lastIndexOf(".");
        if (dotIndex != -1) {
            ext = filename.substring(dotIndex);
        }

        String newFilename = UUID.randomUUID() + ext;
        Path target = uploadDir.resolve(newFilename);

        try{
            multipartFile.transferTo(target.toFile());
        } catch (IOException e) {
            throw new RuntimeException("프로필 이미지 저장 실패", e);
            // throw new SaveProfileImageFailException();
        }


        return "/images/profile/" + newFilename;
    }
}
