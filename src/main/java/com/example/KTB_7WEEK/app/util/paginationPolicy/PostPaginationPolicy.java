package com.example.KTB_7WEEK.app.util.paginationPolicy;

import com.example.KTB_7WEEK.post.entity.Post;
import org.springframework.data.domain.Sort;

import java.util.Comparator;
import java.util.function.Predicate;

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
