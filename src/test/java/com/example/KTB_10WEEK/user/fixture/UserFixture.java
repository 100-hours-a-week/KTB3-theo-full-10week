package com.example.KTB_10WEEK.user.fixture;

import com.example.KTB_10WEEK.user.entity.Role;
import com.example.KTB_10WEEK.user.entity.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

public class UserFixture {
    public static User createUser() {
        return User.builder()
                .email("test@test.com")
                .password("1q2w3e4r!Q")
                .nickname("nickname")
                .profileImage("profileImage")
                .role(Role.USER)
                .build();
    }

    public static User createUserWithId(Long id) {
        LocalDateTime now = LocalDateTime.now();
        return new User(
                id,
                Role.USER,
                "test@test.com",
                "1q2w3e4r!Q",
                "닉네임",
                "https://test.com",
                new HashSet<>(),
                new ArrayList<>(),
                new HashSet<>(),
                now,
                now,
                false
        );
    }
    public static User createUserWithEmailPassword(String email, String password) {
        return User.builder()
                .email(email)
                .password(password)
                .nickname("nickname")
                .profileImage("profileImage")
                .role(Role.USER)
                .build();
    }

    public static User createUserWithNickname(String nickname) {
        return User.builder()
                .email("test@test.com")
                .password("1q2w3e4r!Q")
                .nickname(nickname)
                .profileImage("profileImage")
                .role(Role.USER)
                .build();
    }
}
