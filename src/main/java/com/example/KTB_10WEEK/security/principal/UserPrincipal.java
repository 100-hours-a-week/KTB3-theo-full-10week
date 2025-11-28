package com.example.KTB_10WEEK.security.principal;

import lombok.Getter;

public class UserPrincipal {
    private final Long userId;
    private final String role;

    public UserPrincipal(Long userId, String role) {
        this.userId = userId;
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public String getRole() {
        return role;
    }
}
