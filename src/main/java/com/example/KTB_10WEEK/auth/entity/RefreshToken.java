package com.example.KTB_10WEEK.auth.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_token_id", nullable = false, updatable = true)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "token", nullable = false, unique = true, length = 512)
    private String token;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();


    public void updateToken(String token) {
        this.token = token;
    }

    public RefreshToken() {

    }

    public RefreshToken(Builder builder) {
        this.id = builder.id;
        this.userId = builder.userId;
        this.token = builder.token;
    }

    public static class Builder {
        private Long id;
        private Long userId;
        private String token;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public RefreshToken build() {
            return new RefreshToken(this);
        }
    }
}
