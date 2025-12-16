package com.todayseafood.api.post.dto.response;

import com.todayseafood.api.post.entity.PostLikeId;
import lombok.Getter;

@Getter
public class CancelLikePostResponseDto {
    private long userId;

    private long postId;

    public CancelLikePostResponseDto() {

    }

    public static CancelLikePostResponseDto toDto(PostLikeId postLikeId) {
        CancelLikePostResponseDto dto = new CancelLikePostResponseDto();
        dto.userId = postLikeId.getUserId();
        dto.postId = postLikeId.getPostId();

        return dto;
    }
}
