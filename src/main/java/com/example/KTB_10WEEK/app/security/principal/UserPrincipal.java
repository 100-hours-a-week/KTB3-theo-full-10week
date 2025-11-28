package com.example.KTB_10WEEK.app.security.principal;

import lombok.Getter;

@Getter
public class UserPrincipal {
    private final long userId;
    private final String role;

    public UserPrincipal(long userId, String role) {
        this.userId = userId;
        this.role = role;
    }
}
