package com.example.KTB_7WEEK.post.entity;

import com.example.KTB_7WEEK.user.entity.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Like {

    @EmbeddedId
    private LikeId likeId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "post_id", nullable = false)
//    private Post post;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
