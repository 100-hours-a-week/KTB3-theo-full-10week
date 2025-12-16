package com.todayseafood.api.post.dto.response;

import com.todayseafood.api.post.entity.Post;
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
