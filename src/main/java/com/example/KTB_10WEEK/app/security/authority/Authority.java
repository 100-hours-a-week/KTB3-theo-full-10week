package com.example.KTB_10WEEK.app.security.authority;

import lombok.Getter;

@Getter
public enum Authority {
    // POST
    CREATE_POST("POST:CREATE"),
    DELETE_POST("POST:DELETE"),

    // COMMENT
    CREATE_COMMENT("COMMENT:CREATE"),
    DELETE_COMMENT("COMMENT:DELETE");

    private final String authority;

    Authority(String authority) {
        this.authority = authority;
    }
}
