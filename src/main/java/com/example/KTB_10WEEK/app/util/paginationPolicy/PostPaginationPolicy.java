package com.example.KTB_10WEEK.app.util.paginationPolicy;

import org.springframework.data.domain.Sort;

public enum PostPaginationPolicy implements PaginationPolicy {
    DEFAULT(10, Sort.by("createdAt").descending());

    private final int size;
    private final Sort sort;

    PostPaginationPolicy(int size, Sort sort) {
        this.size = size;
        this.sort = sort;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public Sort sort() {
        return this.sort;
    }
}
