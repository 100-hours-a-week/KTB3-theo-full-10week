package com.example.KTB_7WEEK.post.entity;

import com.example.KTB_7WEEK.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Entity
@Getter
public class PostLike {

    @EmbeddedId
    private PostLikeId likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("postId")
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    protected PostLike() {
    }

    public PostLike(User user, Post post) {
        this.likeId = new PostLikeId(user.getId(), post.getId());
        this.user = user;
        this.post = post;
        this.createdAt = LocalDateTime.now();

        user.getLikes().add(this);
        post.getLikes().add(this);
    }
}
