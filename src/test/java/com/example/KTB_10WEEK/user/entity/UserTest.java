package com.example.KTB_10WEEK.user.entity;

import com.example.KTB_10WEEK.user.fixture.UserFixture;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {


    @Nested
    @DisplayName("생성자 테스트")
    class constructor {
        @Test
        void 기본생성자_생성시_기본값이_올바르게_세팅된다() {
            User user = new User();

            assertNull(user.getId());
            assertEquals(user.getRole(), Role.USER);
            assertNull(user.getEmail());
            assertNull(user.getPassword());
            assertNull(user.getNickname());
            assertNull(user.getProfileImage());

            assertNotNull(user.getPosts());
            assertNotNull(user.getComments());
            assertNotNull(user.getLikes());
            assertNotNull(user.getCreatedAt());
            assertNotNull(user.getUpdatedAt());

            assertEquals(user.getCreatedAt(), user.getUpdatedAt());
        }

        @Test
        void 기본생성자_생성시_컬렉션필드는_Null이_아닌_Empty다() {
            User user = new User();

            assertTrue((user.getPosts() != null) && user.getPosts().isEmpty());
            assertTrue((user.getComments() != null) && user.getComments().isEmpty());
            assertTrue((user.getLikes() != null) && user.getLikes().isEmpty());

        }
    }

    @Nested
    @DisplayName("빌더 테스트")
    class Builder {
        @Test
        void 빌더로_생성하면_정상적으로_필드가_세팅된다() {
            User user = User.builder()
                    .role(Role.USER)
                    .email("email")
                    .password("password")
                    .nickname("nickname")
                    .profileImage("profileImage.png")
                    .build();

            assertEquals(Role.USER, user.getRole());
            assertEquals("email", user.getEmail());
            assertEquals("password", user.getPassword());
            assertEquals("nickname", user.getNickname());
            assertEquals("profileImage.png", user.getProfileImage());

        }
    }

    @Nested
    @DisplayName("Equals, HashCode 테스트")
    class EqualsAndHashCode {
        @Test
        void eqauls_id가_같으면_동등하다() {
            User user = UserFixture.createUserWithId(1L);
            User user1 = UserFixture.createUserWithId(1L);

            assertEquals(user, user1);
        }

        @Test
        void eqauls_id_email_password가_다르면_동등하지_않다() {
            User user = UserFixture.createUserWithId(1L);
            User user1 = UserFixture.createUserWithId(2L);

            assertNotEquals(user, user1);
        }

        @Test
        void equals_target이_null이면_false반환() {
            User user = UserFixture.createUser();
            assertFalse(user.equals(null));
        }

        @Test
        void equals_target이_다른_타입이면_false반환() {
            User user = UserFixture.createUser();
            Object other = "문자열 타입";
            assertFalse(user.equals(other));
        }

        @Test
        void equals_자기자신과는_항상_true() {
            User user = UserFixture.createUser();
            assertTrue(user.equals(user));
        }

        @Test
        void equals가_true면_hashCode도_같다() {
            User user1 = UserFixture.createUserWithId(1L);
            User user2 = UserFixture.createUserWithId(1L);

            assertEquals(user1, user2);
            assertEquals(user1.hashCode(), user2.hashCode());
        }
    }

    @Test
    void updateNowTime_호출하면_현재_시간으로_변경된다() throws InterruptedException {
        User user = UserFixture.createUser();
        LocalDateTime before = user.getUpdatedAt();
        LocalDateTime now = before.plusSeconds(1);

        user.updateNowTime(now);

        assertEquals(now, user.getUpdatedAt());
        assertNotEquals(before, user.getUpdatedAt());
    }

    @Test
    void updateNickname_호출하면_유저의_닉네임이_변경된다() {
        User user = UserFixture.createUser();
        String oldNickname = user.getNickname();

        user.updateNickname("newNickname");

        assertNotEquals(oldNickname, user.getNickname());
    }

    @Test
    void updatePassword_호출하면_유저의_비밀번호가_변경된다() {
        User user = UserFixture.createUser();
        String oldPassword = user.getPassword();

        user.updatePassword("newPassword");

        assertNotEquals(oldPassword, user.getPassword());
    }

    @Test
    void updateProfileImage_호출하면_유저의_프로필_이미지URL이_변경된다() {
        User user = UserFixture.createUser();
        String oldProfileImage = user.getProfileImage();

        user.updateProfileImage("newProfileImage");

        assertNotEquals(oldProfileImage, user.getProfileImage());
    }
}