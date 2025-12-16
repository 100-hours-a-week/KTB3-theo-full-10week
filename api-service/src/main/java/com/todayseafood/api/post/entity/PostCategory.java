package com.todayseafood.api.post.entity;

public enum PostCategory {
    COMMUNITY("소통방"),
    INFO_SHARE("정보공유"),
    COUNSELING("고민상담"),
    NONE("NONE");

    private final String category;

    PostCategory(String category) {
        this.category = category;
    }
}
