package com.example.KTB_7WEEK.auth.dto.response;


import com.example.KTB_7WEEK.post.entity.PostLike;
import com.example.KTB_7WEEK.user.entity.User;
import lombok.Getter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
