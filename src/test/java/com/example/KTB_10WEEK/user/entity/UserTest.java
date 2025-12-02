package com.example.KTB_10WEEK.user.entity;

import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        this.user = new User.Builder()
                .id(1L)
                .role(Role.USER)
                .email("email@email.com")
                .password("1q2w3e4r!Q")
                .nickname("nickname")
                .profileImage("profileImage.png")
                .build();
    }

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
            assertNull(user.getRefreshToken());

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
            User user = new User.Builder()
                    .id(1L)
                    .role(Role.USER)
                    .email("email")
                    .password("password")
                    .nickname("nickname")
                    .profileImage("profileImage.png")
                    .build();

            assertEquals(1L, user.getId());
            assertEquals(Role.USER, user.getRole());
            assertEquals("email", user.getEmail());
            assertEquals("password", user.getPassword());
            assertEquals("nickname", user.getNickname());
            assertEquals("profileImage.png", user.getProfileImage());

        }

        @Test
        void 빌더로_role을_지정하지_않으면_기본값_USER가_세팅된다() {
            User user = new User.Builder().build();

            assertEquals(Role.USER, user.getRole());
        }
    }

    @Nested
    @DisplayName("Equals, HashCode 테스트")
    class EqualsAndHashCode {
        @Test
        void eqauls_id_email_password가_같으면_동등하다() {
            User target = new User.Builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .build();

            assertEquals(user, target);
        }

        @Test
        void eqauls_id_email_password가_다르면_동등하지_않다() {
            Long diffUserId = 0L;
            String diffEmail = "다른 이메일";
            String diffPassword = "다른 비밀번호";

            User target = new User.Builder()
                    .id(diffUserId)
                    .email(diffEmail)
                    .password(diffPassword)
                    .build();

            assertNotEquals(user, target);
        }

        @Test
        void equals_target이_null이면_false반환() {
            assertFalse(user.equals(null));
        }

        @Test
        void equals_target이_다른_타입이면_false반환() {
            Object other = "문자열 타입";
            assertFalse(user.equals(other));
        }

        @Test
        void equals_자기자신과는_항상_true() {
            assertTrue(user.equals(user));
        }

        @Test
        void equals가_true면_hashCode도_같다() {
            User user1 = new User.Builder()
                    .id(1L)
                    .role(Role.USER)
                    .email("email@email.com")
                    .password("1q2w3e4r!Q")
                    .nickname("nickname")
                    .profileImage("profileImage.png")
                    .build();
            User user2 = new User.Builder()
                    .id(1L)
                    .role(Role.USER)
                    .email("email@email.com")
                    .password("1q2w3e4r!Q")
                    .nickname("nickname")
                    .profileImage("profileImage.png")
                    .build();

            assertEquals(user1, user2);
            assertEquals(user1.hashCode(), user2.hashCode());
        }
    }

    @Test
    void updateNowTime_호출하면_현재_시간으로_변경된다() throws InterruptedException {
        LocalDateTime before = user.getUpdatedAt();

        Thread.sleep(5);
        user.updateNowTime();
        LocalDateTime after = user.getUpdatedAt();

        assertTrue(after.isAfter(before));
        assertNotEquals(before, after);
    }

    @Test
    void updateNickname_호출하면_유저의_닉네임이_변경된다() {
        String oldNickname = user.getNickname();

        user.updateNickname("newNickname");

        assertNotEquals(oldNickname, user.getNickname());
    }

    @Test
    void updatePassword_호출하면_유저의_비밀번호가_변경된다() {
        String oldPassword = user.getPassword();

        user.updatePassword("newPassword");

        assertNotEquals(oldPassword, user.getPassword());
    }

    @Test
    void updateProfileImage_호출하면_유저의_프로필_이미지URL이_변경된다() {
        String oldProfileImage = user.getProfileImage();

        user.updateProfileImage("newProfileImage");

        assertNotEquals(oldProfileImage, user.getProfileImage());
    }
}