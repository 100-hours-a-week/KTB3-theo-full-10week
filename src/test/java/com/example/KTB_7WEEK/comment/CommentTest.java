package com.example.KTB_7WEEK.comment;

import com.example.KTB_7WEEK.post.entity.Comment;
import com.example.KTB_7WEEK.post.entity.Post;
import com.example.KTB_7WEEK.post.entity.PostCategory;
import com.example.KTB_7WEEK.post.repository.CommentRepository;
import com.example.KTB_7WEEK.user.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class CommentTest {
    @PersistenceContext
    EntityManager em;

    @Test
    @Rollback(false)
    void createTest() {
        User u = new User.Builder()
                .email("test13@test.com")
                .nickname("special")
                .password("1q2w3e4r!Q")
                .profileImage("https://www.test.com")
                .build();
        em.persist(u);

        Post p = new Post.Builder()
                .author(u)
                .title("title")
                .article("article")
                .category(PostCategory.COMMUNITY)
                .build();
        p.postByAuthor(u);
        em.persist(p);

        Comment c = new Comment.Builder()
                .author(u)
                .post(p)
                .content("댓글")
                .build();
        c.writeBy(u);
        c.onPost(p);
        em.persist(c);
    }
}
