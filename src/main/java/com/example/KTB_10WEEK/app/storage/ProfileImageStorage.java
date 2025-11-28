package com.example.KTB_10WEEK.app.storage;

import com.example.KTB_10WEEK.app.exception.common.SaveImageFailException;
import com.example.KTB_10WEEK.user.exception.SaveProfileImageFailException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
        try {
            return super.saveImage(multipartFile, uploadDir);
        } catch (SaveImageFailException e) {
            throw new SaveProfileImageFailException();
        }
    }

    public String updateProfileImage(MultipartFile multipartFile, String oldFileName) {
        return super.updateImage(multipartFile, uploadDir, oldFileName);
    }

    public boolean deleteProfileImage(String fileName) {
        return super.deleteImage(uploadDir, fileName);
    }

}
