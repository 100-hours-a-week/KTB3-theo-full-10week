package com.example.KTB_10WEEK.user.entity;

import lombok.Getter;

@Getter
public enum Role {
    USER("USER"),
    ADMIN("ADMIN");

    private final String role;

    Role(String role) {
        this.role = role;
    }

}
