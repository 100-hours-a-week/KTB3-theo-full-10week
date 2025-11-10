package com.example.KTB_7WEEK.app.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorage {
    String saveProfileImage(MultipartFile multipartFile);
}
