package com.todayseafood.api.post.dto.response;

import com.todayseafood.api.app.util.DateTimePattern;
import com.todayseafood.api.post.entity.Post;
import com.todayseafood.api.post.entity.PostCategory;
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
