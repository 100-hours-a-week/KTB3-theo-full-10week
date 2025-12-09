package com.example.KTB_10WEEK.post.repository;

import com.example.KTB_10WEEK.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @EntityGraph(attributePaths = {"author"})
    Optional<Post> findById(Long postId);

    @EntityGraph(attributePaths = {"author"})
    Optional<Post> findByIdAndAuthorId(Long postId, Long authorId);


    @EntityGraph(attributePaths = {"author"})
    Page<Post> findAll(Pageable pageable);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE Post p SET p.view_count = p.view_count + 1 WHERE p.id = :postId")
    void increaseViewCount(@Param("postId") Long postId);

}
