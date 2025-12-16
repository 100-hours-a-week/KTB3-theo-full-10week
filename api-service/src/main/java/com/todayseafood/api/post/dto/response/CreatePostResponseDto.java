package com.todayseafood.api.post.dto.response;

import lombok.Getter;

@Getter
public class CreatePostResponseDto {
    private long id;

    public CreatePostResponseDto() {
    }

    public static CreatePostResponseDto toDto(long id) {
        CreatePostResponseDto dto = new CreatePostResponseDto();
        dto.id = id;
        return dto;
    }
}
