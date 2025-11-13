package com.example.KTB_7WEEK.user.dto.response;

import com.example.KTB_7WEEK.app.util.DateTimePattern;
import com.example.KTB_7WEEK.user.entity.User;
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
