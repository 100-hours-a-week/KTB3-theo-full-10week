package com.example.KTB_7WEEK.post.dto.response;

import com.example.KTB_7WEEK.post.entity.Post;
import com.example.KTB_7WEEK.post.entity.PostCategory;
import com.example.KTB_7WEEK.app.util.DateTimePattern;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class UpdateMyPostResponseDto {
    private long id;
    private String title;
    private String article;
    private String articleImage;
    private PostCategory category;
    private String updateAt;

    public UpdateMyPostResponseDto() {
    }
    public static UpdateMyPostResponseDto toDto(Post post) {
        UpdateMyPostResponseDto dto = new UpdateMyPostResponseDto();
        dto.id = post.getId();
        dto.title = post.getTitle();
        dto.article = post.getArticle();
        dto.articleImage = post.getArticleImage();
        dto.category = post.getCategory();
        dto.updateAt = post.getUpdatedAt()
                .format(DateTimeFormatter.ofPattern(DateTimePattern.DEFAULT_DATE_TIME));

        return dto;
    }
}
