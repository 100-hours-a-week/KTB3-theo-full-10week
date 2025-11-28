package com.example.KTB_10WEEK.post.repository;

import com.example.KTB_10WEEK.post.entity.PostLike;
import com.example.KTB_10WEEK.post.entity.PostLikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface PostLikeRepository extends JpaRepository<PostLike, PostLikeId> {
    @Query("SELECT pl.post.id FROM PostLike pl WHERE pl.user.id = :userId")
    Set<Long> findLikePostIdsByUserId(@Param("userId") Long userId);
}
