package com.example.KTB_7WEEK.post.entity;


import com.example.KTB_7WEEK.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.cglib.beans.BulkBean;

import java.time.LocalDateTime;

@Entity
@Getter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", nullable = false, unique = true)
    private Long id = 0L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Column(name = "content", nullable = false, length = 200)
    private String content = "";

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = createdAt;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    public Comment() {
    }

    public Comment(Builder builder) {
        this.id = builder.id;
        this.author = builder.author;
        this.content = builder.content;
    }

    public Comment(String content) {
        this.content = content;
    }

    public void identify(long id) {
        this.id = id;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void updateNow() {
        this.updatedAt = LocalDateTime.now();
    }

    public void softDelete() {
        this.isDeleted = true;
    }

    // 연관관계 편의 메소드 Post
    public void onPost(Post post) {
        this.post = post;
        post.getComments().add(this);
    }

    public void removeInPost() {
        this.post.getComments().remove(this);
        this.post = null;
    }

    // 연관관계 편의 메소드 User
    public void writeBy(User author) {
        this.author = author;
        author.getComments().add(this); // 동기화하는 과정에서 동시성 문제 없나?
    }

    public void deleteByAuthor() {
        this.author.getComments().remove(this);
        this.author = null;
    }


    public static class Builder {
        private Long id = 0L;
        private Post post = new Post();
        private User author = new User();
        private String content = "";

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder post(Post post) {
            this.post = post;
            return this;
        }

        public Builder author(User author) {
            this.author = author;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Comment build() {
            return new Comment(this);
        }
    }
}
