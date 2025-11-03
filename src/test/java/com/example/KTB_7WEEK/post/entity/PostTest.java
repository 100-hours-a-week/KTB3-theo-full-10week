package com.example.KTB_7WEEK.post.entity;

import com.example.KTB_7WEEK.user.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostTest {

    @PersistenceContext
    EntityManager em;

        @Test
        @Rollback(false)
        void tableCreateTest() {
            User user = new User.Builder()
                    .email("email@email.com")
                    .password("1q2w3e4r!Q")
                    .nickname("lllsss")
                    .profileImage("profile_Image")
                    .build();

            em.persist(user);

            Post post = new Post.Builder()
                    .title("sample_title")
                    .article("sample_article")
                    .articleImage("sample_image_url")
                    .category(PostCategory.COMMUNITY)
                .build();

        post.postByAuthor(user);
        em.persist(post);
    }

}