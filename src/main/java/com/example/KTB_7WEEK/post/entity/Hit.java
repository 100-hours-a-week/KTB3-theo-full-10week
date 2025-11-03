package com.example.KTB_7WEEK.post.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Hit {
    @EmbeddedId
    private PostHitId hitId;

//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
//
//    @JoinColumn(name = "post_id", nullable = false)
//    private Post post;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
