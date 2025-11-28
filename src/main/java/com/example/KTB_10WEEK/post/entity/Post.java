package com.example.KTB_10WEEK.post.entity;


import com.example.KTB_10WEEK.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false, unique = true)
    private Long id;

    @Column(name = "title", length = 26, nullable = false)
    private String title;

    @Column(name = "article", nullable = false)
    private String article;

    @Column(name = "article_image")
    private String articleImage;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "category", nullable = false)
    private PostCategory category = PostCategory.NONE;

    @Column(name = "view_count")
    private Long view_count = 0L;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<PostLike> likes = new HashSet<>();

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = this.createdAt;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public Post() {
    }

    public Post(Builder builder) {
        this.id = builder.id;
        this.author = builder.author;
        this.title = builder.title;
        this.article = builder.article;
        this.articleImage = builder.articleImage;
        this.category = builder.category;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateArticle(String article) {
        this.article = article;
    }

    public void updateArticleImage(String articleImage) {
        this.articleImage = articleImage;
    }

    public void updateCategory(PostCategory category) {
        this.category = category;
    }

    public void updateNow() {
        this.updatedAt = LocalDateTime.now();
    }

    public static class Builder {
        private Long id;
        private User author;
        private String title;
        private String article;
        private String articleImage;
        private PostCategory category = PostCategory.NONE;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder author(User author) {
            this.author = author;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder article(String article) {
            this.article = article;
            return this;
        }

        public Builder articleImage(String articleImage) {
            this.articleImage = articleImage;
            return this;
        }

        public Builder category(PostCategory category) {
            this.category = category;
            return this;
        }

        public Post build() {
            return new Post(this);
        }
    }
}
