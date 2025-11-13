package com.example.KTB_7WEEK.app.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Transactional
public class ProfileImageStorage extends ImageStorage {

    private final Path uploadDir;

    public ProfileImageStorage(@Value("${file.upload-dir.profile:uploads/profile}")
                                       String uploadDir) throws IOException {
        this.uploadDir = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(this.uploadDir);
    }

    public String saveProfileImage(MultipartFile multipartFile) {
        String newFilename = super.saveImage(multipartFile, uploadDir);
        return "/images/profile/" + newFilename;
    }

    public String updateProfileImage(MultipartFile multipartFile, String oldFilePath) {
        String updatedFileName = super.updateImage(multipartFile, uploadDir, oldFilePath);
        return "/images/profile/" + updatedFileName;
    }


}
