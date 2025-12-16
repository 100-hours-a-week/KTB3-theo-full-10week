package com.todayseafood.api.post.dto.response;

import com.todayseafood.api.app.util.DateTimePattern;
import com.todayseafood.api.post.entity.PostLike;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class LikePostResponseDto {
    private long userId;

    private long postId;

    private String createdAt;

    public LikePostResponseDto() {
    }

    public static LikePostResponseDto toDto(PostLike postLike) {
        LikePostResponseDto dto = new LikePostResponseDto();
        dto.userId = postLike.getUser().getId();
        dto.postId = postLike.getPost().getId();
        dto.createdAt = postLike.getCreatedAt()
                .format(DateTimeFormatter.ofPattern(DateTimePattern.DEFAULT_DATE_TIME));
        return dto;
    }
}
