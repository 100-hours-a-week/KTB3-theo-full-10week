package com.todayseafood.api.post.dto.response.comment;

import com.todayseafood.api.post.entity.Comment;

public class CreateCommentResponseDto {
    private long id;
    private String content;

    public CreateCommentResponseDto() {
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public static CreateCommentResponseDto toDto(Comment comment) {
        CreateCommentResponseDto dto = new CreateCommentResponseDto();
        dto.id = comment.getId();
        dto.content = comment.getContent();
        return dto;
    }
}
