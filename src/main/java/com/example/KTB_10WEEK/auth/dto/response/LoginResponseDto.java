package com.example.KTB_10WEEK.auth.dto.response;


import com.example.KTB_10WEEK.user.entity.User;
import lombok.Getter;

import java.util.Set;

@Getter
public class LoginResponseDto {
    private long id = 0L;
    private String profileImage = "";
    private String nickname = "";
    private Set<Long> likedPostIds;
    private boolean isLoginSuccess = false;

    public LoginResponseDto() {
    }

    public long getId() {
        return id;
    }

    public boolean isLoginSuccess() {
        return isLoginSuccess;
    }

    public static LoginResponseDto toDto(User user, Set<Long> likedPostIds, boolean isLoginSuccess) {
        LoginResponseDto dto = new LoginResponseDto();
        dto.id = user.getId();
        dto.profileImage = user.getProfileImage();
        dto.nickname = user.getNickname();
        dto.likedPostIds = likedPostIds;
        dto.isLoginSuccess = isLoginSuccess;
        return dto;
    }
}
