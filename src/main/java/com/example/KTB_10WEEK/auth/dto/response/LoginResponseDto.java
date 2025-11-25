package com.example.KTB_10WEEK.auth.dto.response;


import com.example.KTB_10WEEK.user.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.Set;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponseDto {
    private Long id;
    private String profileImage;
    private String nickname;
    private Set<Long> likedPostIds;
    private boolean isLoginSuccess;

    public LoginResponseDto() {
    }

    public long getId() {
        return id;
    }

    public boolean isLoginSuccess() {
        return isLoginSuccess;
    }

    public static LoginResponseDto success(User user, Set<Long> likedPostIds, boolean isLoginSuccess) {
        LoginResponseDto dto = new LoginResponseDto();
        dto.id = user.getId();
        dto.profileImage = user.getProfileImage();
        dto.nickname = user.getNickname();
        dto.likedPostIds = likedPostIds;
        dto.isLoginSuccess = isLoginSuccess;
        return dto;
    }

    public static LoginResponseDto fail() {
        LoginResponseDto dto = new LoginResponseDto();
        dto.isLoginSuccess = false;
        return dto;
    }
}
