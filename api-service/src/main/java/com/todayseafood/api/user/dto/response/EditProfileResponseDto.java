package com.todayseafood.api.user.dto.response;

import com.todayseafood.api.app.util.DateTimePattern;
import com.todayseafood.api.user.entity.User;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class EditProfileResponseDto {
    private long id;
    private String nickname;
    private String profileImage;
    private String updatedAt;

    public EditProfileResponseDto() {

    }

    public static EditProfileResponseDto toDto(User user) {
        EditProfileResponseDto dto = new EditProfileResponseDto();
        dto.id = user.getId();
        dto.nickname = user.getNickname();
        dto.profileImage = user.getProfileImage();
        dto.updatedAt = user.getUpdatedAt()
                .format(DateTimeFormatter.ofPattern(DateTimePattern.DEFAULT_DATE_TIME));
        return dto;
    }
}
