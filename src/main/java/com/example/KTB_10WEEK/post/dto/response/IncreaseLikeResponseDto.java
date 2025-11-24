package com.example.KTB_10WEEK.post.dto.response;

import com.example.KTB_10WEEK.app.util.DateTimePattern;
import com.example.KTB_10WEEK.post.entity.Post;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class IncreaseLikeResponseDto {
    private long id;
    private long like;
    private String updateAt;

    public IncreaseLikeResponseDto() {
    }

    public static IncreaseLikeResponseDto toDto(Post post) {
        IncreaseLikeResponseDto dto = new IncreaseLikeResponseDto();
        dto.id = post.getId();
        dto.like = post.getLikes().size();
        dto.updateAt = post.getUpdatedAt()
                .format(DateTimeFormatter.ofPattern(DateTimePattern.DEFAULT_DATE_TIME));
        return dto;
    }
}
