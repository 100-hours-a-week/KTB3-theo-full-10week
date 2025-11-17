package com.example.KTB_7WEEK.post.dto.response.comment;

import com.example.KTB_7WEEK.app.util.DateTimePattern;
import com.example.KTB_7WEEK.post.entity.Comment;

import java.time.format.DateTimeFormatter;

public class UpdateCommentResponseDto {
    private long id;
    private long postId;
    private long authorId;
    private String content;
    private String createdAt;
    private String updatedAt;

    public UpdateCommentResponseDto() {
    }

    public long getId() {
        return id;
    }

    public long getPostId() {
        return postId;
    }

    public long getAuthorId() {
        return authorId;
    }

    public String getContent() {
        return content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public static UpdateCommentResponseDto toDto(Comment comment) {
        UpdateCommentResponseDto dto = new UpdateCommentResponseDto();
        dto.id = comment.getId();
        dto.postId = comment.getPost().getId();
        dto.authorId = comment.getAuthor().getId();
        dto.content = comment.getContent();
        dto.createdAt = comment.getCreatedAt()
                .format(DateTimeFormatter.ofPattern(DateTimePattern.DEFAULT_DATE_TIME));
        dto.updatedAt = comment.getUpdatedAt()
                .format(DateTimeFormatter.ofPattern(DateTimePattern.DEFAULT_DATE_TIME));

        return dto;
    }
}
