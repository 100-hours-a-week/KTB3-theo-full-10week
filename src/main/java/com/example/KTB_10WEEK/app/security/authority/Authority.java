package com.example.KTB_10WEEK.app.security.authority;

import lombok.Getter;

@Getter
public enum Authority {
    // POST
    CREATE_POST("CREATE_POST"),
    DELETE_POST("DELETE_POST"),

    // COMMENT
    CREATE_COMMENT("CREATE_COMMENT"),
    DELETE_COMMENT("DELETE_COMMENT");

    private final String authority;

    Authority(String authority) {
        this.authority = authority;
    }
}
