package com.example.KTB_7WEEK.post.repository;

import com.example.KTB_7WEEK.post.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @EntityGraph(attributePaths = "author")
    Optional<Comment> findById(long commentId);

    Page<Comment> findAllByPostId(long postId, Pageable pageable);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("DELETE FROM Comment c WHERE c.id = :commentId AND c.post.id = :postId")
    int deleteByIdAndPostId(@Param("commentId") long commentId, @Param("postId") long postId);

}
