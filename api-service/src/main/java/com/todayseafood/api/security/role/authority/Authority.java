package com.todayseafood.api.security.role.authority;

import lombok.Getter;

@Getter
public enum Authority {
    // POST
    CREATE_POST("POST:CREATE"),
    UPDATE_POST("POST:UPDATE"),
    DELETE_POST("POST:DELETE"),

    // COMMENT
    CREATE_COMMENT("COMMENT:CREATE"),
    UPDATE_COMMENT("COMMENT:UPDATE"),
    DELETE_COMMENT("COMMENT:DELETE");

    private final String authority;

    Authority(String authority) {
        this.authority = authority;
    }
}
