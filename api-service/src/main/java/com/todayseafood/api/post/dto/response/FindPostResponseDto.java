package com.todayseafood.api.post.dto.response;

import com.todayseafood.api.app.util.DateTimePattern;
import com.todayseafood.api.post.entity.Post;
import com.todayseafood.api.post.entity.PostCategory;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class FindPostResponseDto {
    private long id = 0L;
    private String authorNickname = "";
    private String authorImage = "";
    private String title = "";
    private String article = "";
    private String articleImage = "";
    private PostCategory category = PostCategory.NONE;

    private long hit = 0;
    private long like = 0;
    private long commentCount = 0;
    private String createdAt = "";
    private String updatedAt = "";
    private boolean isDeleted = false;

    public FindPostResponseDto() {
    }

    public static FindPostResponseDto toDto(Post post) {
        FindPostResponseDto dto = new FindPostResponseDto();
        dto.id = post.getId();
        dto.authorNickname = post.getAuthor().getNickname();
        dto.authorImage = post.getAuthor().getProfileImage();
        dto.title = post.getTitle();
        dto.article = post.getArticle();
        dto.articleImage = post.getArticleImage();
        dto.category = post.getCategory();
        dto.hit = post.getView_count();
        dto.like = post.getLikes().size();
        dto.commentCount = post.getComments().size();
        dto.createdAt = post.getCreatedAt().format(
                DateTimeFormatter.ofPattern(DateTimePattern.DEFAULT_DATE_TIME));
        dto.updatedAt = post.getUpdatedAt().format(
                DateTimeFormatter.ofPattern(DateTimePattern.DEFAULT_DATE_TIME));
        dto.isDeleted = post.isDeleted();
        return dto;
    }
}
