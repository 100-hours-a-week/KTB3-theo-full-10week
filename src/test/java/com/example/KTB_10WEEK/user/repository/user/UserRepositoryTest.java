package com.example.KTB_10WEEK.user.repository.user;

import com.example.KTB_10WEEK.user.entity.Role;
import com.example.KTB_10WEEK.user.entity.User;
import com.example.KTB_10WEEK.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private User createUserWithEmailPassword(String email, String password) {
        return new User.Builder()
                .role(Role.USER)
                .email(email)
                .password(password)
                .nickname("nickname")
                .profileImage("profileImage.png")
                .build();
    }

    private User createUserWithNickname(String nickname) {
        return new User.Builder()
                .role(Role.USER)
                .email("test@test.com")
                .password("1q2w3e4r!Q")
                .nickname(nickname)
                .profileImage("profileImage.png")
                .build();
    }
    @Nested
    @DisplayName("Unique 제약 조건 테스트")
    class UniqueConstraint {
        @Test
        void email필드는_unique하다() {
            User newUser = createUserWithEmailPassword("test@test.com", "1q2w3e4r!Q");
            userRepository.save(newUser);
            userRepository.flush();

            User sameEmailUser = createUserWithEmailPassword("test@test.com", "1q2w3e4r!Q");
            assertThrows(DataIntegrityViolationException.class, () -> {
                userRepository.save(sameEmailUser);
                userRepository.flush();
            });
        }

        @Test
        void nickname필드는_uniqe하다() {
            User newUser = createUserWithNickname("nickname");
            userRepository.save(newUser);
            userRepository.flush();

            User sameNicknameUser = createUserWithNickname("nickname");
            assertThrows(DataIntegrityViolationException.class, () -> {
                userRepository.save(sameNicknameUser);
                userRepository.flush();
            });
        }
    }

    @Nested
    @DisplayName("findByEmail 테스트")
    class FindByEmail {
        @Test
        void findByEmail을_호출하면_이메일_값을_통해서_유저를_가져온다() {
            User newUser = createUserWithEmailPassword("test@test.com", "1q2w3e4r!Q");
            userRepository.save(newUser);

            Optional<User> findUser = userRepository.findByEmail("test@test.com");
            assertTrue(findUser.isPresent());
            assertEquals(newUser, findUser.get());
        }

        @Test
        void 존재하지_않는_유저를_findByEmail하면_빈_Optional_유저를_가져온다() {
            String email = "empty User's email";

            Optional<User> findUser = userRepository.findByEmail(email);

            assertTrue(findUser.isEmpty());
        }
    }

    @Nested
    @DisplayName("findByNickname 테스트")
    class FindByNickname {
        @Test
        void findByNickname을_호출하면_닉네임_값을_통해서_유저를_가져온다() {
            User newUser = createUserWithNickname("nickname");
            userRepository.save(newUser);

            Optional<User> findUser = userRepository.findByNickname("nickname");
            assertTrue(findUser.isPresent());
            assertEquals(findUser.get(), newUser);
        }

        @Test
        void 존재하지_않는_유저를_findByNickname하면_빈_Optional_유저를_가져온다() {
            String nickname = "empty User's nickname";

            Optional<User> findUser = userRepository.findByNickname(nickname);

            assertTrue(findUser.isEmpty());
        }
    }

    @Nested
    @DisplayName("existsByEmail 테스트")
    class ExistsByEmail {
        @Test
        void 유저가_존재할_때_이메일_값을_통해서_existsByEmail을_호출하면_true를_반환한다() {
            User newUser = createUserWithEmailPassword("test@test.com", "1q2w3e4r!Q");
            userRepository.save(newUser);

            boolean isExist = userRepository.existsByEmail("test@test.com");

            assertTrue(isExist);
        }

        @Test
        void 유저가_존재하지_않을_떄_이메일_값을_통해서_existsByEmail을_호출하면_false를_반환한다() {
            boolean isExist = userRepository.existsByEmail("Not Exist Email");

            assertFalse(isExist);
        }
    }

    @Nested
    @DisplayName("existsByNickname 테스트")
    class ExistsByNickname {
        @Test
        void 유저가_존재할_떄_닉네임_값을_통해서_existsByNickname을_호출하면_true를_반환한다() {
            User newUser = createUserWithNickname("nickname");
            userRepository.save(newUser);

            boolean isExist = userRepository.existsByNickname("nickname");

            assertTrue(isExist);
        }

        @Test
        void 유저가_존재하지_않을_떄_닉네임_값을_통해서_existsByNickname을_호출하면_false를_반환한다() {
            boolean isExist = userRepository.existsByNickname("Not Exist Nickname");

            assertFalse(isExist);
        }
    }

}