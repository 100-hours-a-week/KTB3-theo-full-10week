package com.todayseafood.api.post.dto.response.comment;

import com.todayseafood.api.app.util.DateTimePattern;
import com.todayseafood.api.post.entity.Comment;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class FindCommentResponseDto {
    private long id;
    private long authorId;
    private String authorNickname;
    private String authorProfileImage;
    private String content;
    private String createdAt;
    private String updatedAt;

    public FindCommentResponseDto() {
    }

    public static FindCommentResponseDto toDto(Comment comment) {
        FindCommentResponseDto dto = new FindCommentResponseDto();
        dto.id = comment.getId();
        dto.authorId = comment.getAuthor().getId();
        dto.authorNickname = comment.getAuthor().getNickname();
        dto.authorProfileImage = comment.getAuthor().getProfileImage();
        dto.content = comment.getContent();
        dto.createdAt = comment.getCreatedAt()
                .format(DateTimeFormatter.ofPattern(DateTimePattern.DEFAULT_DATE_TIME));
        dto.updatedAt = comment.getUpdatedAt()
                .format(DateTimeFormatter.ofPattern(DateTimePattern.DEFAULT_DATE_TIME));
        return dto;
    }
}
