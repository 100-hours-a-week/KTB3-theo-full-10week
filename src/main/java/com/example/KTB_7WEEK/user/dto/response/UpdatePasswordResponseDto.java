package com.example.KTB_7WEEK.user.dto.response;

import com.example.KTB_7WEEK.app.util.DateTimePattern;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Getter
public class UpdatePasswordResponseDto {
    private long id;
    private String updatedAt;

    public UpdatePasswordResponseDto() {
    }

    public static UpdatePasswordResponseDto toDto(long id) {
        UpdatePasswordResponseDto dto = new UpdatePasswordResponseDto();
        dto.id = id;
        dto.updatedAt = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern(DateTimePattern.DEFAULT_DATE_TIME));
        return dto;
    }
}
