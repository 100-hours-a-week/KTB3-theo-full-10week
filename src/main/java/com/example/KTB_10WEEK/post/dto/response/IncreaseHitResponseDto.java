package com.example.KTB_10WEEK.post.dto.response;

import com.example.KTB_10WEEK.post.entity.Post;
import lombok.Getter;

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
