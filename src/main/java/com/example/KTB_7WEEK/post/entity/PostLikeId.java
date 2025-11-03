package com.example.KTB_7WEEK.post.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;


@Embeddable
public class PostLikeId implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "post_id")
    private Long postId;

    protected PostLikeId() {
    }

    public PostLikeId(Long userId, Long postId) {
        this.userId = userId;
        this.postId = postId;
    }

}
