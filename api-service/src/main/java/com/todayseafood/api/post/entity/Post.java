package com.todayseafood.api.post.entity;


import com.todayseafood.api.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
    @BatchSize(size = 10)
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
    @BatchSize(size = 10)
    private List<Comment> comments = new ArrayList<>();

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

    @Builder
    public Post(User author, String title, String article, String articleImage, PostCategory category) {
        this.author = author;
        this.title = title;
        this.article = article;
        this.articleImage = articleImage;
        this.category = category;

        this.view_count = 0L;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = createdAt;
        this.isDeleted = false;
    }
}
