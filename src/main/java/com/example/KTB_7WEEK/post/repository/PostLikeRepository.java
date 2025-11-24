package com.example.KTB_7WEEK.post.repository;

import com.example.KTB_7WEEK.post.entity.PostLike;
import com.example.KTB_7WEEK.post.entity.PostLikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface PostLikeRepository extends JpaRepository<PostLike, PostLikeId> {
    boolean existsByLikeId(PostLikeId postLikeId);

    // select post_id from post_like where user_id = 18;
    // Entity 기준으로 쓰기
    @Query("SELECT pl.post.id FROM PostLike pl WHERE pl.user.id = :userId")
    Set<Long> findLikePostIdsByUserId(@Param("userId") Long userId);
}
