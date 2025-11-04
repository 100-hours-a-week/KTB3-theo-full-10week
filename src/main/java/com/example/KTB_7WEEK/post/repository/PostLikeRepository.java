package com.example.KTB_7WEEK.post.repository;

import com.example.KTB_7WEEK.post.entity.PostLike;
import com.example.KTB_7WEEK.post.entity.PostLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, PostLikeId> {
    boolean existsByLikeId(PostLikeId postLikeId);
}
