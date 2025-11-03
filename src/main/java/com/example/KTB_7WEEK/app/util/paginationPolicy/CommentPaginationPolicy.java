package com.example.KTB_7WEEK.app.util.paginationPolicy;


import com.example.KTB_7WEEK.post.entity.Comment;
import org.springframework.data.domain.Sort;

import java.util.Comparator;
import java.util.function.Predicate;

public enum CommentPaginationPolicy implements PaginationPolicy {
    DEFAULT(10, Sort.by("createdAt").descending());

    private final int size;
    private final Sort sort;

    CommentPaginationPolicy(int size, Sort sort) {
        this.size = size;
        this.sort = sort;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Sort sort() {
        return this.sort;
    }


}
