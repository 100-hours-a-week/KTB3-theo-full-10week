package com.example.KTB_7WEEK.auth.dto.response;


import com.example.KTB_7WEEK.user.entity.User;
import lombok.Getter;

@Getter
public class LoginResponseDto {
    private long id = 0L;
    private String profileImage = "";
    private boolean isLoginSuccess = false;

    public LoginResponseDto() {
    }

    public long getId() {
        return id;
    }

    public boolean isLoginSuccess() {
        return isLoginSuccess;
    }

    public static LoginResponseDto toDto(User user, boolean isLoginSuccess) {
        LoginResponseDto dto = new LoginResponseDto();
        dto.id = user.getId();
        dto.profileImage = user.getProfileImage();
        dto.isLoginSuccess = isLoginSuccess;
        return dto;
    }
}
