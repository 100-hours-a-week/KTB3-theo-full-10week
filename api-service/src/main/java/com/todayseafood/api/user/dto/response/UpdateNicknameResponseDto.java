package com.todayseafood.api.user.dto.response;

import com.todayseafood.api.app.util.DateTimePattern;
import com.todayseafood.api.user.entity.User;
import lombok.Getter;

import java.time.format.DateTimeFormatter;


@Getter
public class UpdateNicknameResponseDto {
    private long id;
    private String nickname;
    private String updatedAt;

    public UpdateNicknameResponseDto() {
    }

    public static UpdateNicknameResponseDto toDto(User user) {
        UpdateNicknameResponseDto dto = new UpdateNicknameResponseDto();
        dto.id = user.getId();
        dto.nickname = user.getNickname();
        dto.updatedAt = user.getUpdatedAt()
                .format(DateTimeFormatter.ofPattern(DateTimePattern.DEFAULT_DATE_TIME));
        return dto;
    }
}
