package com.example.KTB_7WEEK.post.dto.response;

import com.example.KTB_7WEEK.app.util.DateTimePattern;
import com.example.KTB_7WEEK.post.entity.Post;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class IncreaseHitResponseDto {
    private long id;
    private long viewCount;

    public IncreaseHitResponseDto() {
    }

    public static IncreaseHitResponseDto toDto(Post post) {
        IncreaseHitResponseDto dto = new IncreaseHitResponseDto();
        dto.id = post.getId();
        dto.viewCount = post.getView_count();
        return dto;
    }
}
